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
    private String grant_type;
    private String client_id;
    private String code;

    private String state;
    private String client_secret;

    private NaverTokenRequest(String client_id, String client_secret, String code, String state){
        this.grant_type = "authorization_code";
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.state = state;
    }

    public static NaverTokenRequest of(String clientId, String clientSecret, String code, String state){
        return new NaverTokenRequest(clientId, clientSecret, code, state);
    }

}