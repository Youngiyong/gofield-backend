package com.gofield.infrastructure.internal.api.sns.dto.request;


import lombok.Builder;
import lombok.Getter;

public class SmsRequest {

    @Getter
    @Builder
    public static class SmsCustom {
        private String tel;
        private String messageType;
        private String subject;
        private String content;
    }
}
