package com.gofield.infrastructure.external.api.naver.dto.res;

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
        private String email;
        private String name;
        private String nickname;
    }

}
