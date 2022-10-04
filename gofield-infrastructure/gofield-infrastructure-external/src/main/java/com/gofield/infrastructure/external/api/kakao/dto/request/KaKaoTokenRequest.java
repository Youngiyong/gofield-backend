package com.gofield.infrastructure.external.api.kakao.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KaKaoTokenRequest {
    private String grantType;
    private String clientId;
    private String redirectUri;
    private String code;
    private String clientSecret;
}