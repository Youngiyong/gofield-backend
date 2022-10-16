package com.gofield.api.dto.req;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRefreshRequest {

    @NotNull
    private String refreshToken;
}
