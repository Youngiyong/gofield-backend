package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserProfileResponse {
    private String name;
    private String nickName;
    private String thumbnail;

    private Integer weight;

    private Integer height;

    @Builder
    private UserProfileResponse(String name, String nickName, String thumbnail, Integer weight, Integer height){
        this.name = name;
        this.nickName = nickName;
        this.thumbnail = thumbnail;
        this.weight = weight;
        this.height = height;
    }

    public static UserProfileResponse of(User user, String CDN_URL){
        return UserProfileResponse.builder()
                .name(user.getName())
                .nickName(user.getNickName())
                .thumbnail(user.getThumbnail()==null ? null : CDN_URL.concat(user.getThumbnail()))
                .weight(user.getWeight())
                .height(user.getHeight())
                .build();
    }

}