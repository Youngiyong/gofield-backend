package com.gofield.sns.service;

import com.gofield.sns.model.request.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnsConsumer {
    private final String SQS_TOPIC_SMS = "gofield-sms";
    private final String SQS_TOPIC_USER_PUSH = "gofield-user-push";
    private final String SQS_TOPIC_PARTNER_PUSH = "gofield-partner-push";
    private final String SQS_TOPIC_ADMIN_PUSH = "gofield-admin-push";

    private final SmsService smsService;

    @SqsListener(value = SQS_TOPIC_SMS, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveSmsMessage(@Payload SmsRequest.SmsCustom payload, @Headers Map<String, String> headers) {
        log.info("message received {} {}", payload, headers);
        smsService.sendSms(payload);
    }

//    @SqsListener(value = SQS_TOPIC_USER_PUSH, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//    public void receiveUserPush(@Payload PushRequest request, @Headers Map<String, String> headers) {
//        log.info("message received {} {}", request, headers);
//        pushService.sendPush(request);
//    }
//
//    @SqsListener(value = SQS_TOPIC_PARTNER_PUSH, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//    public void receivePartnerPush(@Payload PushRequest request, @Headers Map<String, String> headers) {
//        log.info("message received {} {}", request, headers);
//        pushService.sendPartnerPush(request);
//    }
}