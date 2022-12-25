package com.gofield.api.controller;

import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.*;
import com.gofield.api.service.OrderService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelTypeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/v1/carrier/{carrierId}/track/{trackId}")
    public ApiResponse<NextUrlResponse> getOrderTrackerDeliveryUrl(@PathVariable String carrierId,
                                                                   @PathVariable String trackId){
        return ApiResponse.success(orderService.getOrderTrackerDeliveryUrl(carrierId, trackId));
    }

    @ApiOperation(value = "주문목록")
    @GetMapping("/v1")
    public ApiResponse<OrderListResponse> getOrderList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                       @RequestParam(required = false) EOrderShippingStatusFlag status){
        return ApiResponse.success(orderService.getOrderList(status, pageable));
    }

    @ApiOperation(value = "구매확정")
    @PostMapping("/v1/{orderNumber}/shipping/{shippingNumber}/complete")
    public ApiResponse completeOrderShipping(@PathVariable String orderNumber,
                                             @PathVariable String shippingNumber){
        orderService.completeOrderShipping(orderNumber, shippingNumber);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "구매내역삭제")
    @DeleteMapping("/v1/{orderNumber}/shipping/{shippingNumber}")
    public ApiResponse deleteOrderShipping(@PathVariable String orderNumber,
                                           @PathVariable String shippingNumber){

        orderService.deleteOrderShipping(orderNumber, shippingNumber);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "주문상품 조회")
    @GetMapping("/v1/{orderNumber}/item/{orderItemId}")
    public ApiResponse<OrderItemResponse> getOrderItem(@PathVariable String orderNumber,
                                                       @PathVariable Long orderItemId){

        return ApiResponse.success(orderService.getOrderItem(orderNumber, orderItemId));
    }

    @ApiOperation(value = "리뷰 작성")
    @PostMapping("/v1/review/{orderItemId}")
    public ApiResponse reviewOrderItem(@PathVariable Long orderItemId,
                                       @RequestPart(value = "review") OrderRequest.OrderReview request,
                                       @RequestPart(value = "images", required = false) List<MultipartFile> images){
        orderService.reviewOrderShippingItem(orderItemId, request, images);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "리뷰 관리 - 리뷰 작성 가능 내역")
    @GetMapping("/v1/review/item")
    public ApiResponse<OrderItemReviewListResponse> getOrderItemReviewList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(orderService.getOrderItemReviewList(pageable));
    }

    @ApiOperation(value = "리뷰 내역")
    @GetMapping("/v1/review")
    public ApiResponse<OrderItemReviewDetailListResponse> gerOrderItemReviewHistoryList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(orderService.getOrderItemReviewDetailList(pageable));
    }

    @ApiOperation(value = "주문 취소/교환/반품 전 상품 정보")
    @GetMapping("/v1/item/{orderItemId}")
    public ApiResponse<OrderCancelItemTempResponse> getOrderItem(@PathVariable Long orderItemId, @RequestParam EOrderCancelReasonFlag reason){
        return ApiResponse.success(orderService.getOrderItemCancelAndChangeAndReturnTemp(orderItemId, reason));
    }

    @ApiOperation(value = "취소/반품/교환 리스트")
    @GetMapping("/v1/cancel")
    public ApiResponse<OrderCancelListResponse> createOrderCancel(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(orderService.getOrderCancelList(pageable));
    }

    @ApiOperation(value = "주문 취소/반품/교환 상세")
    @GetMapping("/v1/cancel/{cancelId}")
    public ApiResponse<OrderCancelDetailResponse> getOrderCancelDetail(@PathVariable Long cancelId){
        return ApiResponse.success(orderService.getOrderCancel(cancelId));
    }

    @ApiOperation(value = "주문 취소")
    @PostMapping("/v1/cancel/{orderItemId}")
    public ApiResponse createOrderCancel(@PathVariable Long orderItemId,
                                         @RequestBody OrderRequest.OrderCancel request){
        orderService.createOrderCancel(orderItemId, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "반풍 요청")
    @PostMapping("/v1/return/{orderItemId}")
    public ApiResponse createOrderReturn(@PathVariable Long orderItemId,
                                         @RequestBody OrderRequest.OrderReturn request){
        orderService.createOrderReturn(orderItemId, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "교환 요청")
    @PostMapping("/v1/change/{orderItemId}")
    public ApiResponse createOrderChange(@PathVariable Long orderItemId,
                                         @RequestBody OrderRequest.OrderChange request){
        orderService.createOrderChange(orderItemId, request);
        return ApiResponse.SUCCESS;
    }
}
