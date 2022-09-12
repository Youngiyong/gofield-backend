package com.gofield.api.model.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}
