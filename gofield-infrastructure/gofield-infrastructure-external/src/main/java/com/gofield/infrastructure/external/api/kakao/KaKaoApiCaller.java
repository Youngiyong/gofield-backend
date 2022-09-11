package com.gofield.infrastructure.external.api.kakao;

import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoTokenResponse;

public interface KaKaoApiCaller {

    KaKaoProfileResponse getProfileInfo(String accessToken);
    KaKaoTokenResponse getToken(String client_id, String redirect_url, String code, String client_secret);

}
