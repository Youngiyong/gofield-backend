package com.gofield.common.api.core.common.dto.response;

import com.gofield.common.model.ErrorAction;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private String code;
    private ErrorAction action;
    private String message;
}