package com.gofield.infrastructure.external.api.naver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverProfileResponse {

    private NaverProfileInfo response;
    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverProfileInfo {
        private String id;
    }

}
