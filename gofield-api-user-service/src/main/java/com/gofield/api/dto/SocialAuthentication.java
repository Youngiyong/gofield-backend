package com.gofield.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
public class SocialAuthentication {
    private String uniqueId;
    private String nickName;

    private String email;

    private SocialAuthentication(String uniqueId, String nickName, String email){
        this.uniqueId = uniqueId;
        this.nickName = nickName;
        this.email = email;
    }

    public static SocialAuthentication of(String uniqueId, String nickName, String email){
        return new SocialAuthentication(uniqueId, nickName, email);
    }
}
