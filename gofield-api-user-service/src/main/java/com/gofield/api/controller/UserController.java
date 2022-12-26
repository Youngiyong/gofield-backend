package com.gofield.api.controller;

import com.gofield.api.dto.enums.TermType;
import com.gofield.api.dto.req.UserRequest;
import com.gofield.api.dto.res.TermResponse;
import com.gofield.api.dto.res.UserAlertResponse;
import com.gofield.api.dto.res.UserProfileResponse;
import com.gofield.api.service.UserService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "휴대폰 번호 변경 - 요청")
    @PostMapping("/v1/sms")
    public ApiResponse accountSms(@Valid @RequestBody UserRequest.UserAccountTel request){
        userService.sendSms(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "휴대폰 번호 변경 - 인증")
    @PutMapping("/v1/sms")
    public ApiResponse certAccountSms(@Valid @RequestBody UserRequest.UserAccountCode request) {
        userService.certSms(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 프로필 조회")
    @GetMapping("/v1/profile")
    public ApiResponse<UserProfileResponse> getProfile(){
        return ApiResponse.success(userService.findUserProfile());
    }

    @ApiOperation(value = "사용자 프로필 업데이트")
    @PutMapping("/v1/profile")
    public ApiResponse uploadProfile(@RequestPart(value = "user") UserRequest.UserProfile request,
                                     @RequestPart(value = "image", required = false) MultipartFile image){
        userService.updateProfile(request, image);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "환불 계좌 조회")
    @GetMapping("/v1/account")
    public ApiResponse getAccountInfo(){
        return ApiResponse.success(userService.findUserAccount());
    }

    @ApiOperation(value = "환불 계좌 업데이트")
    @PutMapping("/v1/account")
    public ApiResponse updateAccountInfo(@Valid @RequestBody UserRequest.UserAccountInfo request) {
        userService.updateAccountInfo(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/v1/withdraw")
    public ApiResponse userWithDraw(){
        userService.userWithDraw();
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 배송지 조회")
    @GetMapping("/v1/address")
    public ApiResponse findUserAddressList(){
        return ApiResponse.success(userService.findUserAddressList());
    }

    @ApiOperation(value = "사용자 배송지 주소 추가")
    @PostMapping("/v1/address")
    public ApiResponse createUserAddress(@Valid @RequestBody UserRequest.UserAddress request){
        userService.createUserAddress(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 배송지 주소 수정")
    @PutMapping("/v1/address/{id}")
    public ApiResponse updateUserAddress(@PathVariable Long id,
                                         @RequestBody UserRequest.UserUpdateAddress request){
        userService.updateUserAddress(id, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "사용자 배송지 주소 삭제")
    @DeleteMapping("/v1/address/{id}")
    public ApiResponse deleteUserAddress(@PathVariable Long id){
        userService.deleteUserAddress(id);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "이용약관 리스트 조회")
    @GetMapping("/v1/term")
    public ApiResponse<List<TermResponse>> getTermList(@RequestParam TermType type){
        return ApiResponse.success(userService.getTermList(type));
    }

    @ApiOperation(value = "사용자 알림 여부 조회")
    @GetMapping("/v1/alert")
    public ApiResponse<UserAlertResponse> getUserAlert(){
        return ApiResponse.success(userService.getUserAlert());
    }

    @ApiOperation(value = "사용자 알림 여부 업데이트")
    @PutMapping("/v1/alert")
    public ApiResponse updateUserAlert(@RequestBody UserRequest.UserAlert request){
        userService.updateUserAlert(request);
        return ApiResponse.SUCCESS;
    }

}
