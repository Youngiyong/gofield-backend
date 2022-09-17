package com.gofield.api.config.security.exception;

import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.common.api.core.common.dto.response.ErrorResponse;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationException implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ErrorCode errorCode = ErrorCode.E401_UNAUTHORIZED;

        ErrorResponse res = ErrorResponse.builder()
                .message(e.getMessage())
                .code(errorCode.getCode())
                .action(ErrorAction.NONE)
                .message(errorCode.getMessage())
                .build();

        ApiResponse apiResponse = ApiResponse.error(res);
        String serializer = new Gson().toJson(apiResponse);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(serializer);
    }
}