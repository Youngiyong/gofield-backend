package com.gofield.infrastructure.external.api.kakao.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class KaKaoTokenRequest {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
    private String client_secret;

    private KaKaoTokenRequest(String client_id, String redirect_uri, String code, String client_secret){
        this.grant_type = "authorization_code";
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.client_secret = client_secret;
    }

    public static KaKaoTokenRequest of(String client_id, String redirect_uri, String code, String client_secret){
        return new KaKaoTokenRequest(client_id, redirect_uri, code, client_secret);
    }

}