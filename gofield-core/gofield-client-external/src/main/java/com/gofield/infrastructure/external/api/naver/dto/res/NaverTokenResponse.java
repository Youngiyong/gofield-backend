package com.gofield.infrastructure.external.api.naver.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverTokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
}