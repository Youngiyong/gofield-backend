package com.gofield.api.model.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessResponse {

    private final String accessKey;
    private final String deviceKey;

    public static AccessResponse of(String accessKey, String deviceKey){
        return new AccessResponse(accessKey, deviceKey);
    }
}
