package com.gofield.sns.controller;

import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.sns.handler.CertTokenHandler;
import com.gofield.sns.model.request.SmsRequest;
import com.gofield.sns.service.SmsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.gofield.sns.handler.CertTokenHandler.validGofieldSnsCertToken;

import javax.validation.Valid;

@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SMSController {

    private final SmsService smsService;

    @PostMapping("/send")
    @ApiOperation(value = "sms 보내기")
    public ApiResponse sendSms(@RequestHeader("certToken") String certToken,
                               @Valid @RequestBody SmsRequest.SmsCustom request){
        validGofieldSnsCertToken(certToken);
        smsService.sendSms(request);
        return ApiResponse.SUCCESS;
    }

}
