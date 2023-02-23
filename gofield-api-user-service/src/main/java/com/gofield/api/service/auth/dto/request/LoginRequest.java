package com.gofield.api.service.auth.dto.request;

import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.user.ESocialFlag;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotNull
    private String code;

    @NotNull
    private ESocialFlag social;

    private EEnvironmentFlag environment;
}
