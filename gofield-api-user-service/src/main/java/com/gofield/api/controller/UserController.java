package com.gofield.api.controller;

import com.gofield.api.model.request.UserRequest;
import com.gofield.api.model.response.UserProfileResponse;
import com.gofield.api.service.UserService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "푸쉬 키 업데이트")
    @PutMapping("/{version}/push")
    public ApiResponse updatePush(@PathVariable("version") EApiVersion apiVersion,
                                  @Valid @RequestBody UserRequest.PushKey request) {
        userService.updatePush(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "휴대폰 번호 변경 - 요청")
    @PostMapping("/{version}/sms")
    public ApiResponse accountSms(@PathVariable("version") EApiVersion apiVersion,
                                  @Valid @RequestBody UserRequest.UserAccountTel request){
        userService.sendSms(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "휴대폰 번호 변경 - 인증")
    @PutMapping("/{version}/sms")
    public ApiResponse certAccountSms(@PathVariable("version") EApiVersion apiVersion,
                                      @Valid @RequestBody UserRequest.UserAccountCode request) {
        userService.certSms(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 프로필 조회")
    @GetMapping("/{version}/profile")
    public ApiResponse<UserProfileResponse> getProfile(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(userService.getUserProfile());
    }

    @ApiOperation(value = "사용자 프로필 업데이트")
    @PutMapping("/{version}/profile")
    public ApiResponse updateProfile(@PathVariable("version") EApiVersion apiVersion,
                                     @Valid @RequestBody UserRequest.UserProfile request){

        userService.updateProfile(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "환불 계좌 조회")
    @PutMapping("/{version}/account")
    public ApiResponse updateAccountInfo(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(userService.getUserAccount());
    }

    @ApiOperation(value = "환불 계좌 업데이트")
    @PutMapping("/{version}/account")
    public ApiResponse updateAccountInfo(@PathVariable("version") EApiVersion apiVersion,
                                         @Valid @RequestBody UserRequest.UserAccountInfo request) {
        userService.updateAccountInfo(request);
        return ApiResponse.SUCCESS;
    }


}
