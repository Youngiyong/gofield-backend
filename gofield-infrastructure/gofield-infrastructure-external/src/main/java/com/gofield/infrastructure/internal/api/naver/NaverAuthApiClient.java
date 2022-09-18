package com.gofield.infrastructure.internal.api.naver;

import com.gofield.infrastructure.internal.api.naver.config.NaverAuthFeignConfig;
import com.gofield.infrastructure.internal.api.naver.dto.response.NaverProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
    name = "NaverAuthApiClient",
    url = "${external.client.naver.profile.base-url}",
    configuration = {
        NaverAuthFeignConfig.class
    }
)
public interface NaverAuthApiClient {

    @GetMapping("${external.client.naver.profile.url}")
    NaverProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);

}
