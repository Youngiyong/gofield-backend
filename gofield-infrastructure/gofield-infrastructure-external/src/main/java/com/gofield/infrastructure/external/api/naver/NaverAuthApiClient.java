package com.gofield.infrastructure.external.api.naver;

import com.gofield.infrastructure.external.api.naver.config.NaverAuthFeignConfig;
import com.gofield.infrastructure.external.api.naver.dto.request.NaverTokenRequest;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
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

    @PostMapping("${external.client.naver.token.url}")
    NaverTokenResponse getToken(@RequestBody NaverTokenRequest request);
}
