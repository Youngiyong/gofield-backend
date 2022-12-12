
package com.gofield.admin.dto.response;

import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.gofield.domain.rds.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String nickname;

    private String email;

    private EStatusFlag status;
    private String createDate;

    private int totalPurchaseAmount;

    @Builder
    private UserDto(Long id, String name, String nickname, String email, EStatusFlag status, String createDate, int totalPurchaseAmount){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.status = status;
        this.createDate = createDate;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public static UserDto of(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickName())
                .email(user.getEmail())
                .status(user.getStatus())
                .createDate(user.getCreateDate().toLocalDate().toString())
                .totalPurchaseAmount(0)
                .build();
    }


}
