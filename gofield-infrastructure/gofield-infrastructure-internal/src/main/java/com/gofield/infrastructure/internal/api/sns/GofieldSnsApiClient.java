package com.gofield.infrastructure.internal.api.sns;

import com.gofield.common.api.core.common.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "KakaoAuthApiClient",
    url = "${internal.client.sns.base-url}",
    configuration = {
            GofieldSnsFeignConfig.class
    }
)
public interface GofieldSnsApiClient {

    @GetMapping("${external.client.sns.single-sms.url}")
    ApiResponse sendSingleMessage(@RequestHeader String certToken);

}
