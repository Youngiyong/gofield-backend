package com.gofield.api.controller;


import com.gofield.api.model.request.LoginAutoRequest;
import com.gofield.api.model.request.LoginRequest;
import com.gofield.api.model.request.TokenRefreshRequest;
import com.gofield.api.model.request.SignupRequest;
import com.gofield.api.model.response.LoginResponse;
import com.gofield.api.model.response.TokenResponse;
import com.gofield.api.service.AuthService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @ApiOperation(value = "회원가입")

    @PostMapping("/{version}/signup")
    public ApiResponse register(@PathVariable("version") EApiVersion apiVersion,
                                @Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/{version}/login")
    public ApiResponse<LoginResponse> login(@PathVariable("version") EApiVersion apiVersion,
                                            @RequestHeader(defaultValue = "eyJjbGllbnRJZCI6Ill4WldiQ041ZHBmMEUxUk9lQVNDZHllSjd5bE44aFFDIn0=") @ApiParam(value = "{\"clientId\":\"YxZWbCN5dpf0E1ROeASCdyeJ7ylN8hQC\"} eyJjbGllbnRJZCI6IkNIRUVTRS1JT1MtQVBQIn0") String secret,
                                            @Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request, secret));
    }

    @ApiOperation(value = "자동 로그인")

    @PostMapping("/{version}/login/auto")
    public ApiResponse<TokenResponse> loginAuth(@PathVariable("version") EApiVersion apiVersion,
                                                @Valid @RequestBody LoginAutoRequest request) {
        return ApiResponse.success(authService.loginAuto(request));
    }

    @ApiOperation(value = "토큰 갱신")
    @PostMapping("/{version}/refresh")
    public ApiResponse<TokenResponse> refresh(@PathVariable("version") EApiVersion apiVersion,
                                              @Valid @RequestBody TokenRefreshRequest request) {
        return ApiResponse.success(authService.refresh(request));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/{version}/logout")
    public ApiResponse logout(@PathVariable("version") EApiVersion apiVersion){
        authService.logout();
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "샘플 토큰 얻어오기")
    @GetMapping("/{version}/sample/{id}")
    public ApiResponse<TokenResponse> getToken(@PathVariable("version") EApiVersion apiVersion,
                                               @PathVariable Long id){
        return ApiResponse.success(authService.getToken(id));
    }
}