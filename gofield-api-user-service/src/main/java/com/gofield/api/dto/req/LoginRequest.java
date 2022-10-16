package com.gofield.api.dto.request;

import com.gofield.domain.rds.enums.ESocialFlag;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotNull
    private String token;
    @NotNull
    private String deviceKey;
    @NotNull
    private ESocialFlag social;

}
