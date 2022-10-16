package com.gofield.infrastructure.external.api.naver.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NaverTokenRequest {
    private String grantType;
    private String clientId;
    private String redirectUri;
    private String code;
    private String clientSecret;

    private NaverTokenRequest(String code, String clientId, String clientSecret, String redirectUri){
        this.grantType = "authorization_code";
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public static NaverTokenRequest of(String code, String clientId, String clientSecret, String redirectUri){
        return new NaverTokenRequest(code, clientId, clientSecret, redirectUri);
    }

}