package com.gofield.api.controller;


import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.*;
import com.gofield.api.service.OrderService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ApiResponse<OrderWaitResponse> redirectPayment(@Valid @RequestBody OrderRequest.OrderPay request){
        return ApiResponse.success(orderService.createOrderWait(request));
    }

    @ApiOperation(value = "주문상세")
    @GetMapping("/v1/{orderNumber}")
    public ApiResponse<OrderDetailResponse> getOrderDetail(@PathVariable String orderNumber){
        return ApiResponse.success(orderService.getOrderDetail(orderNumber));
    }

    @ApiOperation(value = "주문 배송 조회")
    @GetMapping("/v1/{orderNumber}/shipping/{shippingNumber}")
    public ApiResponse<OrderShippingDetailResponse> getOrderShipping(@PathVariable String orderNumber,
                                                                     @PathVariable String shippingNumber){
        return ApiResponse.success(orderService.getOrderShipping(orderNumber, shippingNumber));
    }

    @ApiOperation(value = "배송사 추적")
    @GetMapping("/v1/{carrierId}/{trackId}")
    public ApiResponse<DeliveryTrackResponse> getOrderTrackerDeliveryUrl(@PathVariable String carrierId,
                                                                         @PathVariable String trackId){
        return ApiResponse.success(orderService.getOrderTrackerDeliveryUrl(carrierId, trackId));
    }

    @ApiOperation(value = "주문목록")
    @GetMapping("/v1")
    public ApiResponse<List<OrderResponse>> getOrderList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(orderService.getOrderList(pageable));
    }
}
