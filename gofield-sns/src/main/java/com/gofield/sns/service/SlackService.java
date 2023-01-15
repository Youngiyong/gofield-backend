package com.gofield.sns.service;


import com.gofield.infrastructure.external.api.slack.SlackWebhookApiClient;
import com.gofield.infrastructure.external.api.slack.dto.SlackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackWebhookApiClient slackWebhookApiClient;

    public void sendSlack(SlackRequest request){
        switch (request.getChannel()){
            case QNA:
                slackWebhookApiClient.sendQnaNotification(request.getJson());
                break;
            case ORDER:
                slackWebhookApiClient.sendOrderNotification(request.getJson());
                break;
            case CANCEL:
                slackWebhookApiClient.sendCancelNotification(request.getJson());
                break;
            case RETURN:
                slackWebhookApiClient.sendReturnNotification(request.getJson());
                break;
            case CHANGE:
                slackWebhookApiClient.sendChangeNotification(request.getJson());
                break;
            case EXCEPTION:
                slackWebhookApiClient.sendExceptionNotification(request.getJson());
                break;
        }
    }
}
