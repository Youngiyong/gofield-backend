package com.gofield.api.controller;


import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    @ApiOperation(value = "결제 - 주문 정보 임시 생성")
    @PostMapping("/{version}")
    public ApiResponse createCartTemp(@PathVariable("version") EApiVersion apiVersion,
                                                          @RequestBody List<Map<String, Object>> request){
        return ApiResponse.SUCCESS;
    }

}
