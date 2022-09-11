package com.gofield.common.dto.response;

import com.gofield.common.exception.type.ErrorAction;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private String code;
    private ErrorAction action;
    private String message;
}