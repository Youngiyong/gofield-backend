package com.gofield.infrastructure.internal.api.sns;

import com.gofield.common.model.dto.res.ApiResponse;
import com.gofield.infrastructure.internal.api.sns.dto.request.SmsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "GofieldSnsApiClient",
    url = "${internal.client.sms.base-url}",
    configuration = {
            GofieldSnsFeignConfig.class
    }
)
public interface GofieldSnsApiClient {

    @PostMapping("${internal.client.sms.single.url}")
    ApiResponse sendSingleMessage(@RequestHeader String certToken,
                                  @RequestBody SmsRequest.SmsCustom request);

}
