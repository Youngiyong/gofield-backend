package com.gofield.api.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginAutoResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;

    @Builder
    private LoginAutoResponse(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn){
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public static LoginAutoResponse of(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn){
        return LoginAutoResponse.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }
}
