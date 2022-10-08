package com.gofield.admin.dto.response;

import com.gofield.domain.rds.projections.AdminInfoProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AdminInfoResponse {
    private Long id;
    private String name;
    private String username;

    private String password;
    private String tel;
    private String role;

    @Builder
    private AdminInfoResponse(Long id, String name, String username, String password, String tel, String role){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.role = role;
    }

    public static AdminInfoResponse of(AdminInfoProjection projection){
        return AdminInfoResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .username(projection.getUsername())
                .password("")
                .tel(projection.getTel())
                .role(projection.getRole().getDescription())
                .build();
    }

}
