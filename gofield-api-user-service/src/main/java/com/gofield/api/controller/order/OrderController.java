package com.gofield.api.controller.order;

import com.gofield.api.config.interceptor.Auth;
import com.gofield.api.config.resolver.UserIdResolver;
import com.gofield.api.service.order.dto.request.OrderRequest;
import com.gofield.api.service.order.OrderService;
import com.gofield.api.service.order.dto.response.*;
import com.gofield.common.model.dto.res.ApiResponse;
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

    @ApiOperation(value = "[인증] 구매하기 상품 정보 저장/임시 코드를 발급합니다")
    @Auth
    @PostMapping("/v1/sheet")
    public ApiResponse<OrderSheetCodeResponse> registerOrderSheet(@RequestBody OrderRequest.OrderSheet request){
        return ApiResponse.success(orderService.registerOrderSheet(request, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문 정보, 배송 정보, 저장된 정보를 불러옵니다")
    @Auth
    @GetMapping("/v1/sheet/{uuid}")
    public ApiResponse<OrderSheetResponse> retrieveOrderSheet(@PathVariable String uuid){
        return ApiResponse.success(orderService.retrieveOrderSheet(uuid, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문하기 - 결제를 진행합니다")
    @Auth
    @PostMapping("/v1/payment")
    public ApiResponse<OrderWaitCreateResponse> registerOrderWait(@Valid @RequestBody OrderRequest.OrderPay request){
        return ApiResponse.success(orderService.registerOrderWait(request, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문을 조회합니다")
    @Auth
    @GetMapping("/v1/{orderNumber}")
    public ApiResponse<OrderDetailResponse> retrieveOrder(@PathVariable String orderNumber){
        return ApiResponse.success(orderService.retrieveOrder(orderNumber, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문 배송정보를 조회합니다")
    @Auth
    @GetMapping("/v1/{orderNumber}/shipping/{shippingNumber}")
    public ApiResponse<OrderShippingDetailResponse> retrieveOrderShipping(@PathVariable String orderNumber,
                                                                          @PathVariable String shippingNumber){
        return ApiResponse.success(orderService.retrieveOrderShipping(orderNumber, shippingNumber, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 운송장 배송 정보를 추적합니다")
    @Auth
    @GetMapping("/v1/{shippingNumber}/track")
    public ApiResponse<OrderSheetTempResponse.NextUrlResponse> retrieveOrderTrackerDeliveryUrl(@PathVariable String shippingNumber){
        return ApiResponse.success(orderService.retrieveOrderTrackerDeliveryUrl(shippingNumber, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문 목록을 조회합니다")
    @Auth
    @GetMapping("/v1")
    public ApiResponse<OrderListResponse> retrieveOrders(@PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                         @RequestParam(required = false) EOrderShippingStatusFlag status){
        return ApiResponse.success(orderService.retrieveOrders(status, pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 구매확정을 진행합니다")
    @Auth
    @PostMapping("/v1/{orderNumber}/shipping/{shippingNumber}/complete")
    public ApiResponse completeOrderShipping(@PathVariable String orderNumber,
                                             @PathVariable String shippingNumber){
        orderService.completeOrderShipping(orderNumber, shippingNumber, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 구매내역을 삭제합니다")
    @Auth
    @DeleteMapping("/v1/{orderNumber}/shipping/{shippingNumber}")
    public ApiResponse deleteOrderShipping(@PathVariable String orderNumber,
                                           @PathVariable String shippingNumber){

        orderService.deleteOrderShipping(orderNumber, shippingNumber, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 주문한 상품을 조회합니다")
    @Auth
    @GetMapping("/v1/{orderNumber}/item/{orderItemId}")
    public ApiResponse<OrderItemResponse> retrieveOrderItem(@PathVariable String orderNumber,
                                                            @PathVariable Long orderItemId){

        return ApiResponse.success(orderService.retrieveOrderItem(orderNumber, orderItemId, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문한 상품에 리뷰를 작성합니다")
    @Auth
    @PostMapping("/v1/review/{orderItemId}")
    public ApiResponse reviewOrderItem(@PathVariable Long orderItemId,
                                       @RequestPart(value = "review") OrderRequest.OrderReview request,
                                       @RequestPart(value = "images", required = false) List<MultipartFile> images){
        orderService.reviewOrderItem(orderItemId, request, images, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 주문한 상품중 리뷰가 가능한 내역을 조회합니다")
    @Auth
    @GetMapping("/v1/review/item")
    public ApiResponse<OrderItemReviewListResponse> retrieveOrderItemReviews(@PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(orderService.retrieveOrderItemReviews(pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 작성한 리뷰들을 조회합니다")
    @Auth
    @GetMapping("/v1/review")
    public ApiResponse<OrderItemReviewDetailListResponse> retrieveReviews(@PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(orderService.retrieveReviews(pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문 취소/교환/반품전 상품 정보를 조회합니다")
    @Auth
    @GetMapping("/v1/item/{orderItemId}")
    public ApiResponse<OrderCancelItemTempResponse> retrieveOrderItemBeforeCancel(@PathVariable Long orderItemId){
        return ApiResponse.success(orderService.retrieveOrderItemBeforeCancel(orderItemId, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 취소/반품/교환 주문들을 조회합니다")
    @Auth
    @GetMapping("/v1/cancel")
    public ApiResponse<OrderCancelListResponse> retrieveCancels(@PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(orderService.retrieveCancels(pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 취소/반품/교환 주문을 조회합니다")
    @Auth
    @GetMapping("/v1/cancel/{cancelId}")
    public ApiResponse<OrderCancelDetailResponse> retrieveCancel(@PathVariable Long cancelId){
        return ApiResponse.success(orderService.retrieveCancel(cancelId, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 주문을 취소합니다")
    @Auth
    @PostMapping("/v1/cancel/{orderItemId}")
    public ApiResponse cancelOrder(@PathVariable Long orderItemId,
                                   @RequestBody OrderRequest.OrderCancel request){
        orderService.cancelOrder(orderItemId, request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 상품을 반품합니다")
    @Auth
    @PostMapping("/v1/return/{orderItemId}")
    public ApiResponse returnOrder(@PathVariable Long orderItemId,
                                   @RequestBody OrderRequest.OrderReturn request){
        orderService.returnOrder(orderItemId, request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 상품을 교환합니다")
    @Auth
    @PostMapping("/v1/change/{orderItemId}")
    public ApiResponse createOrderChange(@PathVariable Long orderItemId,
                                         @RequestBody OrderRequest.OrderChange request){
        orderService.changeOrder(orderItemId, request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }
}
