package com.gofield.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {
    private static final String BEARER_TOKEN = "Bearer ";

    public static String withBearerToken(String token) {
        return BEARER_TOKEN + token;
    }

}
