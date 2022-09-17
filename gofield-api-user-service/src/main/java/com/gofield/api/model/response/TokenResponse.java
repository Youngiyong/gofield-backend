package com.gofield.api.model.response;

import lombok.*;

@Getter
@NoArgsConstructor
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;

    @Builder
    private TokenResponse(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn){
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public static TokenResponse of(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn){
        return TokenResponse.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }
}
