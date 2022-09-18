package com.gofield.api.service;


import com.gofield.common.utils.RandomUtils;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoTokenResponse;
import com.gofield.infrastructure.external.api.kakao.test.KaKaoApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Value("${kakao.callback_url}")
    private String KAKAO_CALLBACK_URL;

    private final KaKaoApiCaller kaKaoApiCaller;

    public String callbackLoginAuth(String code, String state){
        KaKaoTokenResponse token = kaKaoApiCaller.getToken(KAKAO_CLIENT_ID, KAKAO_CALLBACK_URL, code, KAKAO_CLIENT_SECRET);
        return token.getAccessToken();
    }

    public String redirect(){
        return KAKAO_AUTH_URL + "?response_type=code&client_id=" +
                KAKAO_CLIENT_ID + "&redirect_uri=" +
                KAKAO_CALLBACK_URL + "&state=" + RandomUtils.makeRandomCode(32);
    }
}
