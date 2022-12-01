package com.gofield.api.controller;

import com.gofield.api.dto.req.CartRequest;
import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.CartResponse;
import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.dto.res.CountResponse;
import com.gofield.api.dto.res.OrderTempResponse;
import com.gofield.api.service.CartService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "장바구니 갯수")
    @GetMapping("/{version}/count")
    public ApiResponse<CountResponse> getCartCount(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(cartService.getCartCount());
    }

    @ApiOperation(value = "바로구매/장바구니 담기")
    @PostMapping("/{version}")
    public ApiResponse createCart(@PathVariable("version") EApiVersion apiVersion,
                                  @Valid @RequestBody CartRequest.Cart request){
        cartService.createCart(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 수량 업데이트")
    @PutMapping("/{version}")
    public ApiResponse updateCart(@PathVariable("version") EApiVersion apiVersion,
                                  @Valid @RequestBody CartRequest.CartQty request){
        cartService.updateCart(request);
        return ApiResponse.SUCCESS;
    }


    @ApiOperation(value = "장바구니 리스트")
    @GetMapping("/{version}")
    public ApiResponse<List<CartResponse>> getCartList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(cartService.getCartList());
    }

    @ApiOperation(value = "장바구니 삭제")
    @DeleteMapping("/{version}/{cartId}")
    public ApiResponse deleteCart(@PathVariable("version") EApiVersion apiVersion,
                                  @PathVariable Long cartId){
        cartService.deleteCart(cartId);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 임시 저장")
    @PostMapping("/{version}/temp")
    public ApiResponse<CommonCodeResponse> createCartTemp(@PathVariable("version") EApiVersion apiVersion,
                                                          @RequestBody List<Map<String, Object>> request){
        return ApiResponse.success(cartService.createCartTemp(request));
    }

    @ApiOperation(value = "임시 주문 정보/배송 정보 불러오기")
    @GetMapping("/{version}/temp/{uuid}")
    public ApiResponse<OrderTempResponse> getCartTemp(@PathVariable("version") EApiVersion apiVersion,
                                                      @PathVariable String uuid){
        return ApiResponse.success(cartService.getCartTemp(uuid));
    }

}
