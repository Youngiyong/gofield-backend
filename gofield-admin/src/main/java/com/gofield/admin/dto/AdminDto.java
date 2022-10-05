package com.gofield.admin.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminDto {
    private Long adminId;
    private String name;
    private String uuid;

    @Builder
    private AdminDto(Long adminId, String name, String uuid){
        this.adminId = adminId;
        this.name = name;
        this.uuid = uuid;
    }

    public static AdminDto of(Long adminId, String name, String uuid){
        return AdminDto.builder()
                .adminId(adminId)
                .name(name)
                .uuid(uuid)
                .build();
    }
}
