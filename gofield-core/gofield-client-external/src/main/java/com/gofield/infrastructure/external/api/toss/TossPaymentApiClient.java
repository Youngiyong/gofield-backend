package com.gofield.infrastructure.external.api.toss;

import com.gofield.infrastructure.external.api.toss.config.TossPaymentApiFeignConfig;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentApproveResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentCancelResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentVirtualResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;

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

    @PostMapping("${external.client.toss.payment.virtual-account-url}")
    TossPaymentVirtualResponse createVirtualAccountPayment(@RequestHeader String Authorization,
                                                           TossPaymentRequest.PaymentVirtual request);

    @PostMapping("${external.client.toss.payment.approve-url}")
    TossPaymentApproveResponse approvePayment(@RequestHeader String Authorization,
                                              TossPaymentRequest.PaymentApprove request);

    @PostMapping("${external.client.toss.payment.cancel-url}")
    TossPaymentCancelResponse cancelPayment(@RequestHeader String Authorization,
                                            @PathVariable String paymentKey,
                                            TossPaymentRequest.PaymentCancel request);


}
