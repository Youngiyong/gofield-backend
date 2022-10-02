package com.gofield.admin.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminSession {
    private Long userId;
    private String name;
    private String uuid;

    @Builder
    private AdminSession(Long userId, String name, String uuid){
        this.userId = userId;
        this.name = name;
        this.uuid = uuid;
    }

    public static AdminSession of(Long userId, String name, String uuid){
        return AdminSession.builder()
                .userId(userId)
                .name(name)
                .uuid(uuid)
                .build();
    }
}
