package com.gofield.api.service.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Authentication {
    private String uuid;
    private Long userId;
    private String issue;

    private Authentication(String uuid, Long userId, String issue){
        this.uuid = uuid;
        this.userId = userId;
        this.issue = issue;
    }

    public static Authentication of(String uuid, Long userId, String issue){
        return new Authentication(uuid, userId, issue);
    }
}
