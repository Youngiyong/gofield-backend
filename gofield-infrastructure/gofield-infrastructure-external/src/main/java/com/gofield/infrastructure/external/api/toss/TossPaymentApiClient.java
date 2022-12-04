package com.gofield.infrastructure.external.api.toss;

import com.gofield.infrastructure.external.api.toss.config.TossPaymentApiFeignConfig;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentApproveResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "TossPaymentApiClient",
    url = "${external.client.toss.payment.base-url}",
    configuration = {
        TossPaymentApiFeignConfig.class
    }
)
public interface TossPaymentApiClient {

    @PostMapping("${external.client.toss.payment.create-url}")
    TossPaymentResponse createPayment(@RequestHeader String Authorization,
                                      TossPaymentRequest.Payment request);

    @PostMapping("${external.client.toss.payment.approve-url}")
    TossPaymentApproveResponse approvePayment(@RequestHeader String Authorization,
                                              TossPaymentRequest.PaymentApprove request);
}
