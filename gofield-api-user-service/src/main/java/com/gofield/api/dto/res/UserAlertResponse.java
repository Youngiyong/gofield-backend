package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserAlertResponse {
    private Boolean isAlertPromotion;

    @Builder
    private UserAlertResponse(Boolean isAlertPromotion){
        this.isAlertPromotion = isAlertPromotion;
    }

    public static UserAlertResponse of(User user){
        return UserAlertResponse.builder()
                .isAlertPromotion(user.getIsAlertPromotion())
                .build();
    }

}