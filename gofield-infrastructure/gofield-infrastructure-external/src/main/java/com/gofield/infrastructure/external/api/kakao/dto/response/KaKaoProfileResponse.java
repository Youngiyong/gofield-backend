package com.gofield.infrastructure.external.api.kakao.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KaKaoProfileResponse {

    private String id;
    private KaKaoAccount kakaoAccount;

    public KaKaoProfileResponse(String id, KaKaoAccount kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }

    public String getEmail() {
        return this.kakaoAccount.getEmail();
    }
    public String getNickName() { return this.kakaoAccount.getProfile().getNickname(); }
    @ToString
    @Getter
    @NoArgsConstructor
    private static class KaKaoAccount {
        private String email;
        private KaKaoAccountProfile profile;

        public KaKaoAccount(String email, KaKaoAccountProfile profile) {
            this.email = email;
            this.profile = profile;
        }

    }

    @ToString
    @Getter
    @NoArgsConstructor
    private static class KaKaoAccountProfile {
        private String nickname;
        public KaKaoAccountProfile(String nickName){ this.nickname = nickname; }
    }
}
