package com.gofield.api.service.cart;

import com.gofield.api.service.cart.dto.request.CartRequest;
import com.gofield.api.service.cart.dto.response.CartResponse;
import com.gofield.api.service.cart.dto.response.CartCountResponse;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.domain.rds.domain.cart.Cart;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;
import com.gofield.domain.rds.domain.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Transactional
    public void deleteCart(Long cartId, Long userId){
        Cart cart = cartRepository.findByCartIdAndUserId(cartId, userId);
        if(cart==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 상품 카트 번호입니다.", cartId));
        }
        cartRepository.delete(cart);
    }

    @Transactional(readOnly = true)
    public CartCountResponse retrieveCount(Long userId){
        return CartCountResponse.of(cartRepository.getCartCount(userId));
    }

    @Transactional
    public void addCart(CartRequest.Cart request, Long userId){
        ItemStock itemStock = itemStockRepository.findByItemNumber(request.getItemNumber());
        if(itemStock==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 상품 번호입니다.", request.getItemNumber()));
        }
        Cart cart = cartRepository.findByUserIdAndItemNumber(userId, request.getItemNumber());
        if(cart!=null){
            cart.updateOne(itemStock.getQty());
            return;
        }
        Item item = itemStock.getItem();
        ItemOption itemOption = itemStock.getType().equals(EItemStockFlag.OPTION) ? itemOptionRepository.findByItemNumber(request.getItemNumber()) : null;
        int price = itemStock.getItem().getIsOption() ? item.getPrice()+itemOption.getOptionPrice() : item.getPrice();
        Boolean isOrder = itemStock.getStatus().equals(EItemStatusFlag.SALE) && itemStock.getQty()>=1 ? true : false;
        cart = Cart.newInstance(userId, request.getItemNumber(), price, 1, isOrder, request.getIsNow());
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCart(CartRequest.CartQty request, Long userId){
        ItemStock itemStock = itemStockRepository.findByItemNumber(request.getItemNumber());
        if(itemStock==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 상품 번호입니다.", request.getItemNumber()));
        }
        if(!itemStock.getStatus().equals(EItemStatusFlag.SALE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "판매중이지 않는 상품입니다.");
        }
        if(request.getQty()>itemStock.getQty()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "상품 재고가 부족하여 수량 추가가 불가합니다.");
        }
        Cart cart = cartRepository.findByUserIdAndItemNumber(userId, request.getItemNumber());
        if(cart==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 장바구니에 존재하지 않는 상품 번호입니다.", request.getItemNumber()));
        }
        cart.updateQty(request.getQty(), itemStock.getQty());
    }

    @Transactional
    public List<CartResponse> retrieveCarts(Long userId){
        List<CartProjection> result = cartRepository.findAllByUserId(userId);
        return CartResponse.of(result);
    }

}
