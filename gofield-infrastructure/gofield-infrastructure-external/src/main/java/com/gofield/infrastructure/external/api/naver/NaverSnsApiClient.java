package com.gofield.infrastructure.external.api.naver;

import com.gofield.infrastructure.external.api.naver.config.NaverSnsFeignConfig;
import com.gofield.infrastructure.external.api.naver.dto.request.NaverSmsRequest;
import com.gofield.infrastructure.external.api.naver.dto.response.NaverSmsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(
    name = "NaverSnsApiClient",
    url = "${external.client.naver.sns.base-url}",
    configuration = {
        NaverSnsFeignConfig.class
    }
)
public interface NaverSnsApiClient {

    @RequestMapping(method = RequestMethod.POST,
            value = "${external.client.naver.sns.url}",
            produces = "application/json")
    NaverSmsResponse sendSms(@RequestHeader("x-ncp-apigw-timestamp") String timsstamp,
                             @RequestHeader("x-ncp-iam-access-key") String accessKey,
                             @RequestHeader("x-ncp-apigw-signature-v2") String signature,
                             NaverSmsRequest.SmsBody smsBody);
}
