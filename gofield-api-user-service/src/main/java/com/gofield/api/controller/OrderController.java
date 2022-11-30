package com.gofield.api.controller;


import com.gofield.api.dto.req.CartRequest;
import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.service.CartService;
import com.gofield.api.service.OrderService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "주문 정보 임시 저장")
    @PostMapping("/{version}/temp")
    public ApiResponse<CommonCodeResponse> insertCartTemp(@PathVariable("version") EApiVersion apiVersion,
                                                          @Valid @RequestBody List<CartRequest.CartTemp> request){

        return ApiResponse.success(orderService.insertCartTemp(request));
    }
}
