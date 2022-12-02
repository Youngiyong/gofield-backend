package com.gofield.api.service;

import com.gofield.api.dto.SocialAuthentication;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.HttpUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.order.OrderWait;
import com.gofield.domain.rds.domain.order.OrderWaitRepository;
import com.gofield.domain.rds.domain.order.Purchase;
import com.gofield.domain.rds.domain.order.PurchaseRepository;
import com.gofield.domain.rds.domain.user.ESocialFlag;
import com.gofield.domain.rds.domain.user.StateLog;
import com.gofield.domain.rds.domain.user.StateLogRepository;
import com.gofield.infrastructure.external.api.kakao.KaKaoAuthApiClient;
import com.gofield.infrastructure.external.api.kakao.KaKaoProfileApiClient;
import com.gofield.infrastructure.external.api.kakao.dto.req.KaKaoTokenRequest;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoTokenResponse;
import com.gofield.infrastructure.external.api.naver.NaverAuthApiClient;
import com.gofield.infrastructure.external.api.naver.NaverProfileApiClient;
import com.gofield.infrastructure.external.api.naver.dto.req.NaverTokenRequest;
import com.gofield.infrastructure.external.api.naver.dto.res.NaverProfileResponse;
import com.gofield.infrastructure.external.api.naver.dto.res.NaverTokenResponse;
import com.gofield.infrastructure.external.api.toss.TossPaymentApiClient;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    @Value("${secret.kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    @Value("${secret.kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${secret.kakao.auth_url}")
    private String KAKAO_AUTH_URL;

    @Value("${secret.naver.client_id}")
    private String NAVER_CLIENT_ID;

    @Value("${secret.naver.client_secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${secret.naver.auth_url}")
    private String NAVER_AUTH_URL;

    @Value("${secret.auth.callback_url}")
    private String AUTH_CALLBACK_URL;

    @Value("${secret.auth.front_local_redirect_url}")
    private String AUTH_FRONT_LOCAL_REDIRECT_URL;

    @Value("${secret.auth.front_service_redirect_url}")
    private String AUTH_FRONT_SERVICE_REDIRECT_URL;

    @Value("${secret.payment.success.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_SERVICE_SUCCESS_REDIRECT_URL;

    @Value("${secret.payment.success.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_LOCAL_SUCCESS_REDIRECT_URL;

    @Value("${secret.payment.fail.front_service_redirect_url}")
    private String AUTH_FRONT_PAYMENT_SERVICE_FAIL_REDIRECT_URL;

    @Value("${secret.payment.fail.front_local_redirect_url}")
    private String AUTH_FRONT_PAYMENT_LOCAL_FAIL_REDIRECT_URL;

    @Value("${secret.toss.success-url}")
    private String PAYMENT_CALLBACK_SUCCESS_URL;

    @Value("${secret.toss.fail-url}")
    private String PAYMENT_CALLBACK_FAIL_URL;

    @Value("${secret.toss.secret-key}")
    private String TOSS_PAYMENT_CLIENT_SECRET;

    private final OrderWaitRepository orderWaitRepository;
    private final TossPaymentApiClient tossPaymentApiClient;
    private final KaKaoAuthApiClient kaKaoAuthApiClient;
    private final KaKaoProfileApiClient kaKaoProfileApiClient;
    private final NaverAuthApiClient naverAuthApiClient;
    private final NaverProfileApiClient naverProfileApiClient;
    private final StateLogRepository stateLogRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional(readOnly = true)
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

    public TossPaymentResponse getPaymentReadyInfo(TossPaymentRequest.Payment request){
        return tossPaymentApiClient.createPayment(TOSS_PAYMENT_CLIENT_SECRET, request);
    }

    public String getTossPaymentSuccessUrl(){
        return PAYMENT_CALLBACK_SUCCESS_URL;
    }

    public String getTossPaymentFailUrl(){
        return PAYMENT_CALLBACK_FAIL_URL;
    }

    @Transactional
    public String callbackPayment(String oid){
        return null;
    }
    @Transactional
    public String redirect(ESocialFlag social, EEnvironmentFlag environment){
        String state = RandomUtils.makeRandomCode(32);
        StateLog stateLog = StateLog.newInstance(state, social, environment);
        stateLogRepository.save(stateLog);
        return makeUrl(state, social);
    }

    @Transactional
    public String callbackSuccessPayment(String orderId, String paymentKey, int amount){
        OrderWait orderWait = orderWaitRepository.findByOid(orderId);
        Purchase purchase = Purchase.newSuccessInstance(orderId, paymentKey, amount);
        purchaseRepository.save(purchase);
        if(orderWait.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            return AUTH_FRONT_PAYMENT_LOCAL_SUCCESS_REDIRECT_URL + orderId;
        } else {
            return AUTH_FRONT_PAYMENT_SERVICE_SUCCESS_REDIRECT_URL + orderId;
        }
    }

    @Transactional
    public String callbackFailPayment(String orderId, String code, String message){
        OrderWait orderWait = orderWaitRepository.findByOid(orderId);
        Purchase purchase = Purchase.newFailInstance(orderId, code, message);
        purchaseRepository.save(purchase);
        if(orderWait.getEnvironment().equals(EEnvironmentFlag.LOCAL)){
            return AUTH_FRONT_PAYMENT_LOCAL_FAIL_REDIRECT_URL + orderId;
        } else {
            return AUTH_FRONT_PAYMENT_SERVICE_FAIL_REDIRECT_URL + orderId;
        }
    }

    public SocialAuthentication getSocialAuthentication(String state, String code, ESocialFlag social){
        if(social.equals(ESocialFlag.KAKAO)){
            KaKaoTokenRequest request = KaKaoTokenRequest.of(KAKAO_CLIENT_ID, AUTH_CALLBACK_URL, code, KAKAO_CLIENT_SECRET);
            KaKaoTokenResponse tokenResponse = kaKaoAuthApiClient.getToken(request);
            KaKaoProfileResponse profileResponse = kaKaoProfileApiClient.getProfileInfo(HttpUtils.withBearerToken(tokenResponse.getAccess_token()));
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

    private String makeUrl(String state, ESocialFlag social){
        switch (social){
            case KAKAO:
                return KAKAO_AUTH_URL + "?response_type=code&client_id=" +
                        KAKAO_CLIENT_ID + "&redirect_uri=" +
                        AUTH_CALLBACK_URL + "&state=" + state;
            case NAVER:
                return NAVER_AUTH_URL + "?response_type=code&client_id=" +
                        NAVER_CLIENT_ID + "&redirect_uri=" +
                        AUTH_CALLBACK_URL + "&state=" + state;
            default:
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원하지 않는 소셜 로그인입니다.");
        }
    }


}
