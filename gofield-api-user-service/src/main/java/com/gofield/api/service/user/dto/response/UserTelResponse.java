package com.gofield.api.service.user.dto.response;

import com.gofield.domain.rds.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserTelResponse {
    private String tel;

    @Builder
    private UserTelResponse(String tel){
        this.tel = tel;
    }

    public static UserTelResponse of(User user){
        return UserTelResponse.builder()
                .tel(user.getTel())
                .build();
    }

}