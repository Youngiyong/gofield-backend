package com.gofield.domain.rds.domain.admin.projection;

import com.gofield.domain.rds.domain.admin.EAdminRole;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class AdminInfoProjection {

    private final Long id;
    private final String name;
    private final String username;
    private final String password;
    private final String tel;
    private final EAdminRole role;
    private final LocalDateTime createDate;


    @QueryProjection
    public AdminInfoProjection(Long id, String name, String username, String password, String tel, EAdminRole role, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.role = role;
        this.createDate = createDate;
    }

}
