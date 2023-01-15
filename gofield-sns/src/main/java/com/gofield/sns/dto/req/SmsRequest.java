package com.gofield.sns.dto.req;

import lombok.Getter;

public class SmsRequest {

    @Getter
    public static class SmsCustom {
        private String tel;
        private String messageType;
        private String subject;
        private String content;
    }
}
