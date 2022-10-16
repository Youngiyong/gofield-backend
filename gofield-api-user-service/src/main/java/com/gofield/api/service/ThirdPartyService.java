package com.gofield.api.service;

import com.gofield.api.dto.SocialAuthentication;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.HttpUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.entity.stateLog.StateLog;
import com.gofield.domain.rds.entity.stateLog.StateLogRepository;
import com.gofield.domain.rds.enums.EEnvironmentFlag;
import com.gofield.domain.rds.enums.ESocialFlag;
import com.gofield.infrastructure.external.api.kakao.KaKaoAuthApiClient;
import com.gofield.infrastructure.external.api.kakao.dto.request.KaKaoTokenRequest;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoTokenResponse;
import com.gofield.infrastructure.external.api.naver.NaverAuthApiClient;
import com.gofield.infrastructure.external.api.naver.NaverProfileApiClient;
import com.gofield.infrastructure.external.api.naver.dto.request.NaverTokenRequest;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverProfileResponse;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    @Value("${kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${kakao.auth_url}")
    private String KAKAO_AUTH_URL;

    @Value("${naver.client_id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client_secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${naver.auth_url}")
    private String NAVER_AUTH_URL;

    @Value("${auth.callback_url}")
    private String AUTH_CALLBACK_URL;

    @Value("${auth.front_local_redirect_url}")
    private String AUTH_FRONT_LOCAL_REDIRECT_URL;

    @Value("${auth.front_service_redirect_url}")
    private String AUTH_FRONT_SERVICE_REDIRECT_URL;

    private final KaKaoAuthApiClient kaKaoAuthApiClient;
    private final NaverAuthApiClient naverAuthApiClient;
    private final NaverProfileApiClient naverProfileApiClient;
    private final StateLogRepository stateLogRepository;


    public String callbackAuth(String code, String state){
        String redirectUrl = null;
        StateLog stateLog = stateLogRepository.findByState(state);
        if(stateLog==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, String.format("<%s> 존재하지 않는 state입니다.", state));
        }
        if(stateLog.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            redirectUrl = AUTH_FRONT_LOCAL_REDIRECT_URL + "?state=" + state + "&code=" + code + "&social=" + stateLog.getSocial().getKey();
        } else {
            redirectUrl = AUTH_FRONT_SERVICE_REDIRECT_URL + "?state=" + state + "&code=" + code + "&social=" + stateLog.getSocial().getKey();
        }
        return redirectUrl;
    }

    public String redirect(ESocialFlag social, EEnvironmentFlag environment){
        String redirectUrl = null;
        String state = RandomUtils.makeRandomCode(32);

        if(social.equals(ESocialFlag.KAKAO)){
            redirectUrl = KAKAO_AUTH_URL + "?response_type=code&client_id=" +
                    KAKAO_CLIENT_ID + "&redirect_uri=" +
                    AUTH_CALLBACK_URL + "&state=" + state;
        } else if(social.equals(ESocialFlag.NAVER)){
            redirectUrl = NAVER_AUTH_URL + "?response_type=code&client_id=" +
                    NAVER_CLIENT_ID + "&redirect_uri=" +
                    AUTH_CALLBACK_URL + "&state=" + state;
        }

        StateLog stateLog = StateLog.newInstance(state, social, environment);
        stateLogRepository.save(stateLog);
        return redirectUrl;
    }

    public SocialAuthentication getAuthentication(String state, String code, ESocialFlag social){
        if(social.equals(ESocialFlag.KAKAO)){
            KaKaoTokenRequest request = KaKaoTokenRequest.of(KAKAO_CLIENT_ID, AUTH_CALLBACK_URL, code, KAKAO_CLIENT_SECRET);
            KaKaoTokenResponse tokenResponse = kaKaoAuthApiClient.getToken(request);
            KaKaoProfileResponse profileResponse = kaKaoAuthApiClient.getProfileInfo(HttpUtils.withBearerToken(tokenResponse.getAccess_token()));
            return SocialAuthentication.of(profileResponse.getId(), profileResponse.getNickName());
        } else if(social.equals(ESocialFlag.NAVER)) {
            NaverTokenRequest request = NaverTokenRequest.of(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, code, state);
            NaverTokenResponse tokenResponse = naverAuthApiClient.getToken(request);
            NaverProfileResponse profileResponse = naverProfileApiClient.getProfileInfo(HttpUtils.withBearerToken(tokenResponse.getAccess_token()));
            return SocialAuthentication.of(profileResponse.getResponse().getId(), null);
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원하지 않는 소셜 로그인입니다.");
        }
    }
}
