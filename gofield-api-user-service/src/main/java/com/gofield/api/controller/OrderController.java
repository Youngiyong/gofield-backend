package com.gofield.api.controller;


import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.dto.res.OrderSheetResponse;
import com.gofield.api.dto.res.OrderWaitResponse;
import com.gofield.api.service.OrderService;
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

    @ApiOperation(value = "구매하기 - 구매 상품 정보 저장/임시 코드 발급")
    @PostMapping("/v1/sheet")
    public ApiResponse<CommonCodeResponse> createOrderSheet(@RequestBody OrderRequest.OrderSheet request){
        return ApiResponse.success(orderService.createOrderSheet(request));
    }

    @ApiOperation(value = "주문 정보- 배송 정보 불러오기")
    @GetMapping("/v1/sheet/{uuid}")
    public ApiResponse<OrderSheetResponse> getOrderSheet(@PathVariable String uuid){
        return ApiResponse.success(orderService.getOrderSheet(uuid));
    }

    @ApiOperation(value = "주문하기 - 결제")
    @PostMapping("/v1/payment")
    public ApiResponse<OrderWaitResponse> redirectPayment(@Valid @RequestBody OrderRequest.Order request){
        return ApiResponse.success(orderService.createOrderWait(request));
    }

}
