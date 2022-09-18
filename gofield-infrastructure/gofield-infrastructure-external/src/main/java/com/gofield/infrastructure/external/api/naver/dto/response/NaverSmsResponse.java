package com.gofield.infrastructure.external.api.naver.dto.response;

import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@AllArgsConstructor
public class NaverSmsResponse {
    private String requestId;
    private String statusCode;
    private String statusName;
    private Timestamp requestTime;
}
