package com.gofield.infrastructure.external.api.kakao;

import com.gofield.infrastructure.external.api.kakao.dto.request.KaKaoTokenRequest;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.response.KaKaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "KakaoAuthApiClient",
    url = "${external.client.kakao.profile.base-url}",
    configuration = {
        KaKaoFeignConfig.class
    }
)
public interface KaKaoAuthApiClient {

    @GetMapping("${external.client.kakao.profile.url}")
    KaKaoProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);

    @PostMapping("${external.client.kakao.token.url}")
    KaKaoTokenResponse getToken(@RequestBody KaKaoTokenRequest request);

}
