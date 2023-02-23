package com.gofield.api.config.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gofield.common.model.dto.res.ApiResponse;
import com.gofield.common.model.dto.res.ErrorResponse;
import com.gofield.common.exception.*;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionAdvice {

    private final ApplicationEventPublisher eventPublisher;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<Object> handleBadRequest(BindException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E400_INVALID_EXCEPTION.getCode(), ErrorAction.NONE, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        InvalidFormatException.class,
        ServletRequestBindingException.class
    })
    protected ApiResponse<Object> handleInvalidFormatException(final Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E400_INVALID_EXCEPTION.getCode(), ErrorAction.NONE, e.getMessage()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private ApiResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage());
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E400_INVALID_EXCEPTION.getCode(), ErrorAction.NONE,  String.format("필수 파라미터 (%s)를 입력해주세요", e.getParameterName())));
    }


    @ExceptionHandler(GofieldBaseException.class)
    private ApiResponse<Object> handleBaseException(GofieldBaseException e, HttpServletRequest request) {
        log.error(e.getMessage());
        return ApiResponse.error(ErrorResponse.of(e.getErrorCode().getCode(), e.getAction(), e.getMessage()));
    }


    /**
     * 405 Method Not Supported
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E405_METHOD_NOT_ALLOWED_EXCEPTION.getCode(), ErrorAction.NONE, ErrorCode.E405_METHOD_NOT_ALLOWED_EXCEPTION.getMessage()));
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ApiResponse<Object> handleHttpMediaTypeException(final MaxUploadSizeExceededException e) {
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E413_PAYLOAD_TOO_LARGE.getCode(), ErrorAction.NONE, ErrorCode.E413_PAYLOAD_TOO_LARGE.getMessage()));
    }

    /**
     * 415 UnSupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ApiResponse<Object> handleHttpMediaTypeException(final HttpMediaTypeException e) {
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E415_UNSUPPORTED_MEDIA_TYPE.getCode(), ErrorAction.NONE, ErrorCode.E415_UNSUPPORTED_MEDIA_TYPE.getMessage()));
    }


    /**
     * 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ApiResponse<Object> handleException(final Exception exception, Throwable throwable) {
        log.error(exception.getMessage(), exception, throwable);
        return ApiResponse.error(ErrorResponse.of(ErrorCode.E500_INTERNAL_SERVER.getCode(), ErrorAction.NONE, ErrorCode.E500_INTERNAL_SERVER.getMessage()));
    }

    @ExceptionHandler(InternalRuleException.class)
    public ApiResponse handleInternalRuleException(InternalRuleException e, HttpServletResponse response) {
        try {
            response.setStatus(499);
        } catch (Exception ex) {
            e.printStackTrace();
        }
        return ApiResponse.error(ErrorResponse.of(e.getErrorCode().getCode(), e.getAction(), e.getMessage()));
    }
}
