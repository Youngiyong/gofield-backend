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

    private SocialAuthentication(String uniqueId, String nickName){
        this.uniqueId = uniqueId;
        this.nickName = nickName;
    }

    public static SocialAuthentication of(String uniqueId, String nickName){
        return new SocialAuthentication(uniqueId, nickName);
    }
}
