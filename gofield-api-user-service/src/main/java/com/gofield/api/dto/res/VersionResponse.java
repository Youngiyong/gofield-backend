package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VersionResponse {
    private String type;
    private String message;
    private String marketUrl;

    @Builder
    private VersionResponse(String type, String message, String marketUrl){
        this.type = type;
        this.message = message;
        this.marketUrl = marketUrl;
    }

    public static VersionResponse of(String type, String message, String marketUrl){
        return VersionResponse.builder()
                .type(type)
                .message(message)
                .marketUrl(marketUrl)
                .build();
    }
}
