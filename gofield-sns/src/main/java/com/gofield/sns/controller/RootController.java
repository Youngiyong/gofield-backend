package com.gofield.sns.controller;

import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.sns.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RootController {

    @GetMapping("/")
    public ApiResponse health(){
        return ApiResponse.success("OK");
    }

}
