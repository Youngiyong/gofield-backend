package com.gofield.infrastructure.external.api.kakao;

import com.gofield.infrastructure.external.api.kakao.config.KaKaoAuthApiFeignConfig;
import com.gofield.infrastructure.external.api.kakao.dto.req.KaKaoTokenRequest;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "KakaoAuthApiClient",
    url = "${external.client.kakao.token.base-url}",
    configuration = {
        KaKaoAuthApiFeignConfig.class
    }
)
public interface KaKaoAuthApiClient {

    @PostMapping(value = "${external.client.kakao.token.url}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KaKaoTokenResponse getToken(@RequestBody KaKaoTokenRequest request);

}
