package com.gofield.api.controller;

import com.gofield.api.dto.req.LoginRequest;
import com.gofield.api.dto.req.TokenRefreshRequest;
import com.gofield.api.dto.req.SignupRequest;
import com.gofield.api.dto.res.LoginResponse;
import com.gofield.api.dto.res.TokenResponse;
import com.gofield.api.service.AuthService;
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
    @PostMapping("/v1/signup")
    public ApiResponse<TokenResponse> signup(@RequestHeader String Authorization,
                                             @Valid @RequestBody SignupRequest request) {
        return ApiResponse.success(authService.signup(Authorization, request));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/v1/login")
    public ApiResponse<LoginResponse> login(@RequestHeader(defaultValue = "eyJjbGllbnRJZCI6Ill4WldiQ041ZHBmMEUxUk9lQVNDZHllSjd5bE44aFFDIn0=") @ApiParam(value = "{\"clientId\":\"YxZWbCN5dpf0E1ROeASCdyeJ7ylN8hQC\"} eyJjbGllbnRJZCI6IkNIRUVTRS1JT1MtQVBQIn0") String secret,
                                            @Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request, secret));
    }

    @ApiOperation(value = "토큰 갱신")
    @PostMapping("/v1/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestHeader String Authorization,
                                              @Valid @RequestBody TokenRefreshRequest request) {
        return ApiResponse.success(authService.refresh(Authorization, request));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/v1/logout")
    public ApiResponse logout(@RequestHeader String Authorization){
        authService.logout(Authorization);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "샘플 토큰 얻어오기")
    @GetMapping("/v1/sample/{id}")
    public ApiResponse<TokenResponse> getToken(@PathVariable Long id){
        return ApiResponse.success(authService.getToken(id));
    }
}