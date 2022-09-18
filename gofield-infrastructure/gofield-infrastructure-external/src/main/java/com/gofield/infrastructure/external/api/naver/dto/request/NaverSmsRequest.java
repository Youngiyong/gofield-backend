package com.gofield.infrastructure.external.api.naver.dto.request;

import lombok.*;

import java.util.List;

public class NaverSmsRequest {

    @Builder
    @Getter
    public static class SmsBody{
        private String type;
        private String from;
        private String subject;
        private String content;
        private List<SmsMessage> messages;
    }

    @Builder
    @Getter
    public static class SmsMessage{
        private String to;
    }
}
