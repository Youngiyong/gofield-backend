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

    @PostMapping(value = "${external.client.slack.webhook.exception-url}", produces = "application/json")
    void sendExceptionNotification(@RequestBody JSONObject request);
    @PostMapping(value = "${external.client.slack.webhook.order-url}", produces = "application/json")
    void sendOrderNotification(@RequestBody JSONObject request);

    @PostMapping(value = "${external.client.slack.webhook.cancel-url}", produces = "application/json")
    void sendCancelNotification(@RequestBody JSONObject request);

    @PostMapping(value = "${external.client.slack.webhook.return-url}", produces = "application/json")
    void sendReturnNotification(@RequestBody JSONObject request);

    @PostMapping(value = "${external.client.slack.webhook.change-url}", produces = "application/json")
    void sendChangeNotification(@RequestBody JSONObject request);

    @PostMapping(value = "${external.client.slack.webhook.qna-url}", produces = "application/json")
    void sendQnaNotification(@RequestBody JSONObject request);
}
