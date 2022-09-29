package com.gofield.admin.advice;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.common.api.core.common.dto.response.ErrorResponse;
import com.gofield.common.exception.*;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdvice {

    /**
     * 400 BadRequest
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<Object> handleBadRequest(BindException e) {
        log.error(e.getMessage(), e);

        ErrorCode errorCode = ErrorCode.E400_INVALID_EXCEPTION;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        InvalidFormatException.class,
        ServletRequestBindingException.class
    })
    protected ApiResponse<Object> handleInvalidFormatException(final Exception e) {
        log.error(e.getMessage(), e);

        ErrorCode errorCode = ErrorCode.E400_INVALID_EXCEPTION;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidException.class)
    protected ApiResponse<Object> handleValidationException(final InvalidException e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 401 UnAuthorized
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    protected ApiResponse<Object> handleUnAuthorizedException(final UnAuthorizedException e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 403 Forbidden
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    protected ApiResponse<Object> handleForbiddenException(final ForbiddenException e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 404 NotFound
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected ApiResponse<Object> handleNotFoundException(final NotFoundException e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 405 Method Not Supported
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        ErrorCode errorCode = ErrorCode.E405_METHOD_NOT_ALLOWED_EXCEPTION;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 409 Conflict
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    protected ApiResponse<Object> handleConflictException(final ConflictException e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 409 loadUserByUsername AuthenticationException
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ApiResponse<Object> handleAuthenticationException(final AuthenticationException e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(ErrorCode.E400_INVALID_EXCEPTION.getCode())
                .message(String.format(e.getMessage()))
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 415 UnSupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ApiResponse<Object> handleHttpMediaTypeException(final HttpMediaTypeException e) {
        ErrorCode errorCode = ErrorCode.E415_UNSUPPORTED_MEDIA_TYPE;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 502 Bad Gateway
     */
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(BadGatewayException.class)
    protected ApiResponse<Object> handleBadGatewayException(final BadGatewayException exception) {
        log.error(exception.getMessage(), exception);

        ErrorCode errorCode = ErrorCode.E502_BAD_GATEWAY;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ApiResponse<Object> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);

        ErrorCode errorCode = ErrorCode.E500_INTERNAL_SERVER;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

}
