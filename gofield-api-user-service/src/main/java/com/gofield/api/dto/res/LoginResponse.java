package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Boolean isFirst;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;

    @Builder
    private LoginResponse(Boolean isFirst, String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn){
        this.isFirst = isFirst;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public static LoginResponse of(Boolean isFirst, String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn){
        return LoginResponse.builder()
                .isFirst(isFirst)
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }
}
