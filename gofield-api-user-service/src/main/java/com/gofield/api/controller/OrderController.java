package com.gofield.api.controller;


import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.dto.res.OrderSheetResponse;
import com.gofield.api.service.CartService;
import com.gofield.api.service.OrderService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;

    private final OrderService orderService;

    @ApiOperation(value = "구매하기 - 구매 상품 정보 저장/임시 코드 발급")
    @PostMapping("/{version}/sheet")
    public ApiResponse<CommonCodeResponse> createOrderSheet(@PathVariable("version") EApiVersion apiVersion,
                                                            @RequestBody List<Map<String, Object>> request){
        return ApiResponse.success(cartService.createOrderSheet(request));
    }

    @ApiOperation(value = "주문 정보- 배송 정보 불러오기")
    @GetMapping("/{version}/sheet/{uuid}")
    public ApiResponse<OrderSheetResponse> getOrderSheet(@PathVariable("version") EApiVersion apiVersion,
                                                         @PathVariable String code){
        return ApiResponse.success(cartService.getOrderSheetTemp(code));
    }



}
