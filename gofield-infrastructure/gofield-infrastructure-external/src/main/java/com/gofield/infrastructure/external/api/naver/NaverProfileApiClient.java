package com.gofield.infrastructure.external.api.naver;

import com.gofield.infrastructure.external.api.naver.config.NaverProfileFeignConfig;
import com.gofield.infrastructure.external.api.naver.dto.res.NaverProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
    name = "NaverProfileApiClient",
    url = "${external.client.naver.profile.base-url}",
    configuration = {
        NaverProfileFeignConfig.class
    }
)
public interface NaverProfileApiClient {

    @GetMapping("${external.client.naver.profile.url}")
    NaverProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);
}
