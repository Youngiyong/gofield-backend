package com.gofield.common.api.core.common.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    public static final ApiResponse<String> SUCCESS = success("OK");

    private Boolean status;
    private String message;
    private ErrorResponse error;

    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "", null, data);
    }
    public static ApiResponse<Object> error(ErrorResponse error) {
        return new ApiResponse<>(false,  null, error, null);
    }

    public static <T> ApiResponse<T> error(ErrorResponse error, String message) {
        return new ApiResponse<>(false, message, error, null);
    }

}
