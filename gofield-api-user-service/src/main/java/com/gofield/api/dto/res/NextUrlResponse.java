package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NextUrlResponse {
    private String nextUrl;

    @Builder
    private NextUrlResponse(String nextUrl){
        this.nextUrl = nextUrl;
    }

    public static NextUrlResponse of(String nextUrl){
        return NextUrlResponse.builder()
                .nextUrl(nextUrl)
                .build();
    }
}
