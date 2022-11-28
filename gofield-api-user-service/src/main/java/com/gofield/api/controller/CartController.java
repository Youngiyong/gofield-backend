package com.gofield.api.controller;

import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.CartResponse;
import com.gofield.api.dto.res.CountResponse;
import com.gofield.api.service.CartService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
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
    @GetMapping("/{version}/count")
    public ApiResponse<CountResponse> getCartCount(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(cartService.getCartCount());
    }

    @ApiOperation(value = "바로구매/장바구니 담기")
    @PostMapping("/{version}")
    public ApiResponse insertCart(@PathVariable("version") EApiVersion apiVersion,
                                  @Valid @RequestBody ItemRequest.Cart request){

        cartService.insertCart(request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 리스트")
    @GetMapping("/{version}")
    public ApiResponse<List<CartResponse>> getCartList(@PathVariable("version") EApiVersion apiVersion){

        return ApiResponse.success(cartService.getCartList());
    }

}
