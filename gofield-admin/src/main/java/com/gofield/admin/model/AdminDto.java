package com.gofield.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto {
    private Long userId;
    private String userName;
    private Long companyId;
    private String companyName;
    private String companyImage;
    private String role;
}
