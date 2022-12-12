package com.gofield.admin.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminInfo {
    private Long adminId;
    private String name;
    private String uuid;

    @Builder
    private AdminInfo(Long adminId, String name, String uuid){
        this.adminId = adminId;
        this.name = name;
        this.uuid = uuid;
    }

    public static AdminInfo of(Long adminId, String name, String uuid){
        return AdminInfo.builder()
                .adminId(adminId)
                .name(name)
                .uuid(uuid)
                .build();
    }
}
