//package com.gofield.api.listner;
//
//import com.gofield.api.service.sns.SNSService;
//import com.gofield.api.util.SlackUtil;
//import com.gofield.common.model.SlackChannel;
//import com.gofield.common.utils.LocalDateTimeUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ErrorService {
//
//    private final SNSService snsService;
//
//    @Value("${server.monitoring}")
//    private String SERVER_MONITORING_NAME;
//
//    public void sendSlackErrorNotification(String exception, String message) {
//        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        JSONObject request = SlackUtil.makeError(SERVER_MONITORING_NAME, servletRequest.getRequestURI(), exception, LocalDateTimeUtils.LocalDateTimeNowToString(), message, null);
//        snsService.sendSlack(SlackChannel.EXCEPTION, request);
//    }
//}
