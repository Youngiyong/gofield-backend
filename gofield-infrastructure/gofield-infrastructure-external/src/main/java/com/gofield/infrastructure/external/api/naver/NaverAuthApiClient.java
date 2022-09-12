package com.gofield.infrastructure.external.api.naver;

import com.gofield.infrastructure.external.api.naver.dto.response.NaverProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
    name = "NaverAuthApiClient",
    url = "${external.client.naver.profile.base-url}",
    configuration = {
        NaverFeignConfig.class
    }
)
public interface NaverAuthApiClient {

    @GetMapping("${external.client.naver.profile.url}")
    NaverProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);

}
