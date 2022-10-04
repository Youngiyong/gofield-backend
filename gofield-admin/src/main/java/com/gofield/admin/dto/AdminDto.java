package com.gofield.admin.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminDto {
    private Long userId;
    private String name;
    private String uuid;

    @Builder
    private AdminDto(Long userId, String name, String uuid){
        this.userId = userId;
        this.name = name;
        this.uuid = uuid;
    }

    public static AdminDto of(Long userId, String name, String uuid){
        return AdminDto.builder()
                .userId(userId)
                .name(name)
                .uuid(uuid)
                .build();
    }
}
