package com.gofield.infrastructure.external.api.naver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class NaverProfileResponse {

    private NaverProfileInfo response;
    @ToString
    @Getter
    @AllArgsConstructor
    private static class NaverProfileInfo {
        private String id;
    }

}
