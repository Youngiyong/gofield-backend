package com.gofield.api.advice;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gofield.api.service.ErrorService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.common.api.core.common.dto.response.ErrorResponse;
import com.gofield.common.exception.*;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdvice {

    /**
     * 400 BadRequest
     */

    private final ErrorService errorService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<Object> handleBadRequest(BindException e) {
        log.error(e.getMessage(), e);
        errorService.sendSlackNotification(BindException.class.getSimpleName(), e.getMessage());

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
        errorService.sendSlackNotification(InvalidFormatException.class.getSimpleName(), e.getMessage());

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
        errorService.sendSlackNotification(InvalidException.class.getSimpleName(), e.getMessage());

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
        errorService.sendSlackNotification(ConflictException.class.getSimpleName(), e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    /**
     * 415 UnSupported Media Type
     */
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ApiResponse<Object> handleHttpMediaTypeException(final MaxUploadSizeExceededException e) {
        ErrorCode errorCode = ErrorCode.E413_PAYLOAD_TOO_LARGE;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
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
        errorService.sendSlackNotification(ConflictException.class.getSimpleName(), exception.getMessage());

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
        errorService.sendSlackNotification(Exception.class.getSimpleName(), exception.getMessage());

        ErrorCode errorCode = ErrorCode.E500_INTERNAL_SERVER;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(ErrorAction.NONE)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

    @ExceptionHandler(InternalRuleException.class)
    public ApiResponse handleInternalRuleException(InternalRuleException e, HttpServletResponse response) {
        try {
            response.setStatus(499);
        } catch (Exception ex) {
            e.printStackTrace();
        }
        errorService.sendSlackNotification(InternalRuleException.class.getSimpleName(), e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .action(e.getAction())
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return ApiResponse.error(errorResponse);
    }

}
