package com.gofield.infrastructure.external.api.slack;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "SlackWebhookApiClient",
    url = "${external.client.slack.webhook.base-url}",
    configuration = {
        SlackWebhookApiFeignConfig.class
    }
)
public interface SlackWebhookApiClient {

    @PostMapping(value = "${external.client.slack.webhook.url}", produces = "application/json")
    void sendNotificationMessage(@RequestBody JSONObject request);

}
