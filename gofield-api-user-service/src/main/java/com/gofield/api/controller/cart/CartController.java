package com.gofield.api.controller.cart;

import com.gofield.api.config.interceptor.Auth;
import com.gofield.api.config.resolver.UserIdResolver;
import com.gofield.api.service.cart.dto.request.CartRequest;
import com.gofield.api.service.cart.dto.response.CartResponse;
import com.gofield.api.service.cart.dto.response.CartCountResponse;
import com.gofield.api.service.cart.CartService;
import com.gofield.common.model.dto.res.ApiResponse;
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

    @ApiOperation(value = "[인증] 장바구니 갯수를 확인합니다")
    @Auth
    @GetMapping("/v1/count")
    public ApiResponse<CartCountResponse> retrieveCount(){
        return ApiResponse.success(cartService.retrieveCount(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 바로구매, 장바구니에 담습니다.")
    @Auth
    @PostMapping("/v1")
    public ApiResponse addCart(@Valid @RequestBody CartRequest.Cart request){
        cartService.addCart(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 수량 업데이트")
    @Auth
    @PutMapping("/v1")
    public ApiResponse updateCart(@Valid @RequestBody CartRequest.CartQty request){
        cartService.updateCart(request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "장바구니 리스트")
    @Auth
    @GetMapping("/v1")
    public ApiResponse<List<CartResponse>> retrieveCarts(){
        return ApiResponse.success(cartService.retrieveCarts(UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "장바구니 삭제")
    @Auth
    @DeleteMapping("/v1/{cartId}")
    public ApiResponse deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }
}
