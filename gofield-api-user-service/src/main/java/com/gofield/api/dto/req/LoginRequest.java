package com.gofield.api.dto.req;

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
    private String state;

    @NotNull
    private ESocialFlag social;

}
