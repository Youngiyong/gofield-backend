package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.EAdminRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDto {
    private Long id;
    private String name;
    private String username;

    private String password;
    private EAdminRole role;
    private String createDate;

    @Builder
    private AdminDto(Long id, String name, String username,String password, EAdminRole role, String createDate){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createDate = createDate;
    }

    public static AdminDto of(Admin admin){
        return AdminDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .username(admin.getUsername())
                .role(admin.getAdminRole().getRole())
                .createDate(admin.getCreateDate().toLocalDate().toString())
                .build();
    }


}
