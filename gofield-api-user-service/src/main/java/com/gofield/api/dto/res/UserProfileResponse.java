package com.gofield.api.dto.res;

import com.gofield.domain.rds.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserProfileResponse {
    private String name;
    private String nickName;
    private Integer weight;
    private Integer height;
    private String thumbnail;


    @Builder
    private UserProfileResponse(String name, String nickName, Integer weight, Integer height, String thumbnail){
        this.name = name;
        this.nickName = nickName;
        this.weight = weight;
        this.height = height;
        this.thumbnail = thumbnail;
    }

    public static UserProfileResponse of(User user, String CDN_URL){
        return UserProfileResponse.builder()
                .name(user.getName())
                .nickName(user.getNickName())
                .weight(user.getWeight())
                .height(user.getHeight())
                .thumbnail(CDN_URL.concat(user.getThumbnail()))
                .build();
    }
}
