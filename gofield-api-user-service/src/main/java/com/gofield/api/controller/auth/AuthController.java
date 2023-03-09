package com.gofield.api.controller.auth;

import com.gofield.api.config.interceptor.Auth;
import com.gofield.api.config.resolver.UserIdResolver;
import com.gofield.api.service.auth.dto.request.LoginRequest;
import com.gofield.api.service.auth.dto.request.TokenRefreshRequest;
import com.gofield.api.service.auth.dto.request.SignupRequest;
import com.gofield.api.service.auth.dto.response.LoginResponse;
import com.gofield.api.service.auth.dto.response.TokenResponse;
import com.gofield.api.service.auth.AuthService;
import com.gofield.common.model.dto.res.ApiResponse;
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

    @ApiOperation(value = "[인증] 회원가입을 진행합니다")
    @Auth
    @PostMapping("/v1/signup")
    public ApiResponse<TokenResponse> signup(@RequestHeader String Authorization,
                                             @Valid @RequestBody SignupRequest request) {
        return ApiResponse.success(authService.signup(Authorization, request, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "로그인을 진행합니다")
    @PostMapping("/v1/login")
    public ApiResponse<LoginResponse> login(@RequestHeader(defaultValue = "eyJjbGllbnRJZCI6Ill4WldiQ041ZHBmMEUxUk9lQVNDZHllSjd5bE44aFFDIn0=") @ApiParam(value = "{\"clientId\":\"YxZWbCN5dpf0E1ROeASCdyeJ7ylN8hQC\"} eyJjbGllbnRJZCI6IkNIRUVTRS1JT1MtQVBQIn0") String secret,
                                            @Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request, secret));
    }

    @ApiOperation(value = "[인증] 엑세스 토큰을 갱신합니다")
    @PostMapping("/v1/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestHeader String Authorization,
                                              @Valid @RequestBody TokenRefreshRequest request) {
        return ApiResponse.success(authService.refresh(Authorization, request));
    }

    @ApiOperation(value = "[인증] 로그아웃을 진행합니다")
    @Auth
    @PostMapping("/v1/logout")
    public ApiResponse logout(@RequestHeader String Authorization){
        authService.logout(Authorization);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[임시] 샘플 토큰 얻어오기")
    @GetMapping("/v1/sample/{id}")
    public ApiResponse<TokenResponse> getToken(@RequestHeader(defaultValue = "YxZWbCN5dpf0E1ROeASCdyeJ7ylN8hQC", required = false)String secret, @PathVariable Long id){
        return ApiResponse.success(authService.getToken(id, secret));
    }
}