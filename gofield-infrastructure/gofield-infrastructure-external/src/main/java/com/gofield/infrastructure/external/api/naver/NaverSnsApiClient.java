package com.gofield.infrastructure.external.api.naver;

import com.gofield.infrastructure.external.api.naver.config.NaverAuthFeignConfig;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
    name = "NaverSnsApiClient",
    url = "${external.client.naver.sns.base-url}",
    configuration = {
        NaverAuthFeignConfig.class
    }
)
public interface NaverSnsApiClient {

    @GetMapping("${external.client.naver.sns.url}")
    NaverProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);

}