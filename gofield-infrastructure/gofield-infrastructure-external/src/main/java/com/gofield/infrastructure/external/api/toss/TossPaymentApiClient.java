package com.gofield.infrastructure.external.api.toss;

import com.gofield.infrastructure.external.api.toss.config.TossPaymentApiFeignConfig;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient(
    name = "TossPaymentApiClient",
    url = "${external.client.toss.payment.base-url}",
    configuration = {
        TossPaymentApiFeignConfig.class
    }
)
public interface TossPaymentApiClient {

    @PostMapping("${external.client.toss.payment.url}")
    TossPaymentResponse createPayment(@RequestHeader String Authorization,
                                      TossPaymentRequest.Payment request);
}
