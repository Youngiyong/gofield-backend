package com.gofield.infrastructure.external.api.naver;

import com.gofield.infrastructure.external.api.naver.config.NaverAuthFeignConfig;
import com.gofield.infrastructure.external.api.naver.dto.req.NaverTokenRequest;
import com.gofield.infrastructure.external.api.naver.dto.res.NaverTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "NaverAuthApiClient",
    url = "${external.client.naver.token.base-url}",
    configuration = {
        NaverAuthFeignConfig.class
    }
)
public interface NaverAuthApiClient {

    @PostMapping(value = "${external.client.naver.token.url}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    NaverTokenResponse getToken(@RequestBody NaverTokenRequest request);
}
