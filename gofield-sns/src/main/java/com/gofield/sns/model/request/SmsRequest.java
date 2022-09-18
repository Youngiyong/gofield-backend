package com.gofield.sns.model.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SmsRequest {

    @Getter
    public static class SmsCustom {
        @NotNull(message = "전화번호는 필수값입니다.")
        @Pattern(regexp = "(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})", message = "전화번호 형식이 맞지 않습니다.")
        private String tel;
        @NotNull(message = "메세지 타입은 필수값입니다.")
        private String messageType;
        @NotNull(message = "제목은 필수값입니다.")
        private String subject;
        @NotNull(message = "본문 내용은 필수값입니다.")
        private String content;
    }
}
