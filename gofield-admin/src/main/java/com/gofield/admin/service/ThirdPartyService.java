package com.gofield.admin.service;


import com.gofield.infrastructure.external.api.toss.TossPaymentApiClient;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentCancelResponse;
import com.gofield.infrastructure.external.api.tracker.TrackerApiClient;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    @Value("${secret.toss.secret-key}")
    private String TOSS_PAYMENT_CLIENT_SECRET;

    private final TrackerApiClient trackerApiClient;
    private final TossPaymentApiClient tossPaymentApiClient;

    public TossPaymentCancelResponse cancelPayment(String paymentKey, TossPaymentRequest.PaymentCancel request){
        return tossPaymentApiClient.cancelPayment(TOSS_PAYMENT_CLIENT_SECRET, paymentKey, request);
    }

    public CarrierTrackResponse getCarrierTrackInfo(String carrierId, String trackId){
        return trackerApiClient.getCarrierTrackInfo(carrierId, trackId);
    }
}
