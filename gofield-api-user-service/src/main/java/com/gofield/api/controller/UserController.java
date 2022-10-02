package com.gofield.api.controller;

import com.gofield.api.dto.request.UserRequest;
import com.gofield.api.dto.response.UserProfileResponse;
import com.gofield.api.service.UserService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return ApiResponse.success(userService.findUserProfile());
    }

    @ApiOperation(value = "사용자 프로필 업데이트")
    @PutMapping("/{version}/profile")
    public ApiResponse updateProfile(@PathVariable("version") EApiVersion apiVersion,
                                     @Valid @RequestBody UserRequest.UserProfile request){

        userService.updateProfile(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "프로필 이미지 업데이트")
    @PostMapping("/{version}/profile/upload")
    public ApiResponse<String> uploadProfile(@PathVariable("version") EApiVersion apiVersion,
                                             @RequestPart("profile")  MultipartFile profile){
        return ApiResponse.success(userService.uploadProfile(profile));
    }

    @ApiOperation(value = "환불 계좌 조회")
    @GetMapping("/{version}/account")
    public ApiResponse getAccountInfo(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(userService.findUserAccount());
    }

    @ApiOperation(value = "환불 계좌 업데이트")
    @PutMapping("/{version}/account")
    public ApiResponse updateAccountInfo(@PathVariable("version") EApiVersion apiVersion,
                                         @Valid @RequestBody UserRequest.UserAccountInfo request) {
        userService.updateAccountInfo(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/{version}/withdraw")
    public ApiResponse userWithDraw(@PathVariable("version") EApiVersion apiVersion){
        userService.userWithDraw();
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 배송지 조회")
    @GetMapping("/{version}/address")
    public ApiResponse findUserAddressList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(userService.findUserAddressList());
    }

    @ApiOperation(value = "사용자 배송지 주소 추가")
    @PostMapping("/{version}/address")
    public ApiResponse insertUserAddress(@PathVariable("version") EApiVersion apiVersion,
                                         @Valid @RequestBody UserRequest.UserAddress request){
        userService.insertUserAddress(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 배송지 주소 수정")
    @PutMapping("/{version}/address/{id}")
    public ApiResponse updateUserAddress(@PathVariable("version") EApiVersion apiVersion,
                                         @PathVariable Long id,
                                         @RequestBody UserRequest.UserUpdateAddress request){
        userService.updateUserAddress(id, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 배송지 주소 삭제")
    @DeleteMapping("/{version}/address/{id}")
    public ApiResponse deleteUserAddress(@PathVariable("version") EApiVersion apiVersion,
                                         @PathVariable Long id){
        userService.deleteUserAddress(id);
        return ApiResponse.SUCCESS;
    }

}
