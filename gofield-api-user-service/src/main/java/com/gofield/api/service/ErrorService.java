package com.gofield.api.service;

import com.gofield.api.util.SlackUtil;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.infrastructure.external.api.slack.SlackWebhookApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService {
    private final SlackWebhookApiClient slackWebhookApiClient;

    @Value("${server.monitoring}")
    private String SERVER_MONITORING_NAME;

    public void sendSlackNotification(String exception, String message) {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        JSONObject request = SlackUtil.makeError(SERVER_MONITORING_NAME, servletRequest.getRequestURI(), exception, LocalDateTimeUtils.LocalDateTimeToString(), message);
        slackWebhookApiClient.sendNotificationMessage(request);
    }
}
