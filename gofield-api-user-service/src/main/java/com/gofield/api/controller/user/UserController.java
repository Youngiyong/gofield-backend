package com.gofield.api.controller.user;

import com.gofield.api.config.interceptor.Auth;
import com.gofield.api.config.resolver.UserIdResolver;
import com.gofield.api.service.user.dto.TermType;
import com.gofield.api.service.user.dto.response.TermResponse;
import com.gofield.api.service.user.dto.request.UserRequest;
import com.gofield.api.service.user.UserService;
import com.gofield.api.service.user.dto.response.UserAccountResponse;
import com.gofield.api.service.user.dto.response.UserAlertResponse;
import com.gofield.api.service.user.dto.response.UserProfileResponse;
import com.gofield.api.service.user.dto.response.UserTelResponse;
import com.gofield.common.model.dto.res.ApiResponse;
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

    @ApiOperation(value = "[인증] - 휴대폰 번호 변경 문자 인증을 요청합니다")
    @Auth
    @PostMapping("/v1/sms")
    public ApiResponse sendSms(@Valid @RequestBody UserRequest.UserAccountTel request){
        userService.sendSms(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] - 휴대폰 번호 변경 문자 인증을 인증합니다")
    @Auth
    @PutMapping("/v1/sms")
    public ApiResponse certSms(@Valid @RequestBody UserRequest.UserAccountCode request) {
        userService.certSms(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] - 사용자 프로필을 조회합니다")
    @Auth
    @GetMapping("/v1/profile")
    public ApiResponse<UserProfileResponse> getProfile(){
        return ApiResponse.success(userService.retrieveProfile(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] - 사용자 프로필을 수정합니다")
    @Auth
    @PutMapping("/v1/profile")
    public ApiResponse uploadProfile(@RequestPart(value = "user", required = false) UserRequest.UserProfile request,
                                     @RequestPart(value = "image", required = false) MultipartFile image){
        userService.updateProfile(request, image, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] - 환불 계좌를 조회합니다")
    @Auth
    @GetMapping("/v1/account")
    public ApiResponse<UserAccountResponse> getAccountInfo(){
        return ApiResponse.success(userService.retrieveAccount(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] - 휴대폰 번호를 조회합니다")
    @Auth
    @GetMapping("/v1/tel")
    public ApiResponse<UserTelResponse> getTel(){
        return ApiResponse.success(userService.retrieveTel(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 환불 계좌를 수정합니다")
    @Auth
    @PutMapping("/v1/account")
    public ApiResponse updateAccount(@Valid @RequestBody UserRequest.UserAccountInfo request) {
        userService.updateAccountInfo(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] - 회원을 탈퇴합니다")
    @Auth
    @DeleteMapping("/v1/withdraw")
    public ApiResponse withDraw(){
        userService.userWithDraw(UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] - 배송지 주소들을 조회합니다")
    @Auth
    @GetMapping("/v1/address")
    public ApiResponse getAddresses(){
        return ApiResponse.success(userService.retrieveAddresses(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] - 배송지 주소를 추가합니다")
    @Auth
    @PostMapping("/v1/address")
    public ApiResponse registerAddress(@Valid @RequestBody UserRequest.UserAddress request){
        userService.registerAddress(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[안증] - 배송지 주소를 수정합니다")
    @Auth
    @PutMapping("/v1/address/{id}")
    public ApiResponse updateAddress(@PathVariable Long id,
                                     @RequestBody UserRequest.UserUpdateAddress request){
        userService.updateAddress(id, request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] - 배송지 주소를 삭제합니다")
    @Auth
    @DeleteMapping("/v1/address/{id}")
    public ApiResponse deleteAddress(@PathVariable Long id){
        userService.deleteAddress(id, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "이용약관 리스트를 조회합니다")
    @GetMapping("/v1/term")
    public ApiResponse<List<TermResponse>> getTerms(@RequestParam TermType type){
        return ApiResponse.success(userService.getTermList(type));
    }

    @ApiOperation(value = "[인증] - 사용자 알림 여부들을 조회합니다")
    @Auth
    @GetMapping("/v1/alert")
    public ApiResponse<UserAlertResponse> getAlerts(){
        return ApiResponse.success(userService.getUserAlert(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] - 사용자 알림 여부를 수정합니다")
    @Auth
    @PutMapping("/v1/alert")
    public ApiResponse updateAlert(@RequestBody UserRequest.UserAlert request){
        userService.updateUserAlert(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

}
