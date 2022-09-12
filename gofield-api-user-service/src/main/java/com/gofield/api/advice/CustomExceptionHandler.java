package com.gofield.api.advice;

import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.common.api.core.common.dto.response.ErrorResponse;
import com.gofield.common.exception.model.InternalRuleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InternalRuleException.class)
    public ApiResponse handleGitlabException(InternalRuleException e, HttpServletResponse response) {
        try {
            response.setStatus(499);
        } catch (Exception ex) {
            e.printStackTrace();
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(e.getAction())
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }
}
