package com.gofield.api.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginAutoRequest {
    @NotNull
    private String accessKey;
    @NotNull
    private String deviceKey;
}
