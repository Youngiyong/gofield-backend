package com.gofield.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppleTokenPayload {
    private String iss;
    private String aud;
    private Long exp;
    private Long iat;
    private String sub;
    private String c_hash;
    private Long auth_time;
    private Boolean nonce_supported;
    private Boolean email_verified;
    private String email;

    private AppleTokenPayload(String sub, String email) {
        this.sub = sub;
        this.email = email;
    }
}
