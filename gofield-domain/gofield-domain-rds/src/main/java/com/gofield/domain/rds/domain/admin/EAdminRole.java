package com.gofield.domain.rds.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EAdminRole {
    ROLE_ADMIN("주관리자", "A"),
    ROLE_OPERATOR("운영자", "O");

    private String description;
    private String code;
}
