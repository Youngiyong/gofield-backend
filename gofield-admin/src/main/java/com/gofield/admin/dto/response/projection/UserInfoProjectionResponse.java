
package com.gofield.admin.dto.response.projection;

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
public class UserInfoProjectionResponse {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private EStatusFlag status;
    private String createDate;

    private int totalPurchaseAmount;

    @Builder
    private UserInfoProjectionResponse(Long id, String name, String nickname, String email, EStatusFlag status, String createDate, int totalPurchaseAmount){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.status = status;
        this.createDate = createDate;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public static UserInfoProjectionResponse of(Long id, String name, String nickname, String email, EStatusFlag status, String createDate, int totalPurchaseAmount){
        return UserInfoProjectionResponse.builder()
                .id(id)
                .name(name)
                .nickname(nickname)
                .email(email)
                .status(status)
                .createDate(createDate)
                .totalPurchaseAmount(totalPurchaseAmount)
                .build();
    }

    /*
    ToDo: 총결제금액 추후 수정
     */
    public static List<UserInfoProjectionResponse> of(List<User> list){
        return list
                .stream()
                .map(p -> UserInfoProjectionResponse.of(p.getId(), p.getName(), p.getNickName(), p.getEmail(), p.getStatus(), p.getCreateDate().toLocalDate().toString(), 0))
                .collect(Collectors.toList());
    }


}
