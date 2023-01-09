package com.gofield.api.controller;

import com.gofield.api.dto.req.CartRequest;
import com.gofield.api.dto.res.CartResponse;
import com.gofield.api.dto.res.CountResponse;
import com.gofield.api.service.CartService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "장바구니 갯수")
    @GetMapping("/v1/count")
    public ApiResponse<CountResponse> getCartCount(){
        return ApiResponse.success(cartService.getCartCount());
    }

    @ApiOperation(value = "바로구매/장바구니 담기")
    @PostMapping("/v1")
    public ApiResponse createCart(@Valid @RequestBody CartRequest.Cart request){
        cartService.createCart(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 수량 업데이트")
    @PutMapping("/v1")
    public ApiResponse updateCart(@Valid @RequestBody CartRequest.CartQty request){
        cartService.updateCart(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 리스트")
    @GetMapping("/v1")
    public ApiResponse<List<CartResponse>> getCartList(){
        return ApiResponse.success(cartService.getCartList());
    }

    @ApiOperation(value = "장바구니 삭제")
    @DeleteMapping("/v1/{cartId}")
    public ApiResponse deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
        return ApiResponse.SUCCESS;
    }

}
