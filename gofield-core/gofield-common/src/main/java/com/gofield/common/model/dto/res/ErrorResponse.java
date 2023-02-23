package com.gofield.common.model.dto.res;

import com.gofield.common.model.ErrorAction;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private String code;
    private ErrorAction action;
    private String message;

    @Builder
    private ErrorResponse(String code, ErrorAction action, String message){
        this.code = code;
        this.action = action;
        this.message = message;
    }

    public static ErrorResponse of(String code, ErrorAction action, String message){
        return ErrorResponse.builder()
                .code(code)
                .action(action)
                .message(message)
                .build();
    }
}