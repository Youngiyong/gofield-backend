package com.gofield.infrastructure.external.api.kakao;

import com.gofield.infrastructure.external.api.kakao.config.KaKaoAuthApiFeignConfig;
import com.gofield.infrastructure.external.api.kakao.dto.req.KaKaoTokenRequest;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoProfileResponse;
import com.gofield.infrastructure.external.api.kakao.dto.res.KaKaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "KakaoProfileApiClient",
    url = "${external.client.kakao.profile.base-url}",
    configuration = {
        KaKaoAuthApiFeignConfig.class
    }
)
public interface KaKaoProfileApiClient {
    @GetMapping("${external.client.kakao.profile.url}")
    KaKaoProfileResponse getProfileInfo(@RequestHeader("Authorization") String accessToken);
}
