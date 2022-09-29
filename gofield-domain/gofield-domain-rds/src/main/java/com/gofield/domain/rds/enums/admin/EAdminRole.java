package com.gofield.domain.rds.enums.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EAdminRole {
    ROLE_ADMIN("관리자", "M"),
    ROLE_CEO("대표", "C"),
    ROLE_DEVELOP("개발팀" ,"D");

    private String description;
    private String code;
}
