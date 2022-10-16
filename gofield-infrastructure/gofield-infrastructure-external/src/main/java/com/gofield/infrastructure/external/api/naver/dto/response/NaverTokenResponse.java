package com.gofield.infrastructure.external.api.naver.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NaverTokenResponse {
    private String accessToken;
    private String tokenType;
    private String idToken;
    private int expiresIn;
    private String scope;
    private int refreshTokenExpiresIn;
}