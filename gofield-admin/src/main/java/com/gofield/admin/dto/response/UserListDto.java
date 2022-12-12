package com.gofield.admin.dto.response;

import com.gofield.admin.dto.response.projection.AdminInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.UserInfoProjectionResponse;
import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import com.gofield.domain.rds.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class UserListDto {

    private List<UserInfoProjectionResponse> list;
    private Page<User> page;

    @Builder
    private UserListDto(List<UserInfoProjectionResponse> list, Page<User> page){
        this.list = list;
        this.page = page;
    }

    public static UserListDto of(List<UserInfoProjectionResponse> list, Page<User> page){
        return UserListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
