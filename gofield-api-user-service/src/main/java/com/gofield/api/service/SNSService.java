package com.gofield.api.service;

import com.gofield.infrastructure.internal.api.sns.dto.request.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SNSService {

    @Value("${secret.sqs.topic.sms}")
    private String SQS_SMS_TOPIC;
    private final QueueMessagingTemplate messagingTemplate;

    public void sendSms(SmsRequest.SmsCustom request){
        messagingTemplate.convertAndSend(SQS_SMS_TOPIC, request);
    }

}
