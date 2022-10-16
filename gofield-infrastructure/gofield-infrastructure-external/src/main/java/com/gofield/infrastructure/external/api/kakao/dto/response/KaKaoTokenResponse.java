package com.gofield.infrastructure.external.api.kakao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoTokenResponse {
    private String access_token;
    private String token_type;
    private String id_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}