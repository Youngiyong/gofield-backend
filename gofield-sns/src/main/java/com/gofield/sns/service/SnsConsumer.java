package com.gofield.sns.service;

import com.gofield.infrastructure.external.api.slack.dto.SlackRequest;
import com.gofield.sns.dto.req.SmsRequest;
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
    private final String SQS_SLACK_TOPIC = "gofield-slack";
    private final SmsService smsService;
    private final SlackService slackService;

    @SqsListener(value = SQS_TOPIC_SMS, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveSmsMessage(@Payload SmsRequest.SmsCustom payload, @Headers Map<String, String> headers) {
        log.info("message received {} {}", payload, headers);
        smsService.sendSms(payload);
    }

    @SqsListener(value = SQS_SLACK_TOPIC, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveUserPush(@Payload SlackRequest request, @Headers Map<String, String> headers) {
        log.info("message received {} {}", request, headers);
        slackService.sendSlack(request);
    }

}