package com.gofield.api.service;

import com.gofield.api.dto.req.CartRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.cart.Cart;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.order.OrderSheetRepository;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final UserService userService;

    private final CommonService commonService;

    private final CartRepository cartRepository;
    private final OrderSheetRepository orderSheetRepository;

    private final ItemStockRepository itemStockRepository;
    private final ItemOptionRepository itemOptionRepository;

    @Transactional
    public void deleteCart(Long cartId){
        User user = userService.getUserNotNonUser();
        Cart cart = cartRepository.findByCartIdAndUserId(cartId, user.getId());
        if(cart==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 상품 카트 번호입니다.", cartId));
        }
        cartRepository.delete(cart);
    }

    @Transactional(readOnly = true)
    public CountResponse getCartCount(){
        User user = userService.getUserNotNonUser();
        return CountResponse.of(cartRepository.getCartCount(user.getId()));
    }

    @Transactional
    public void createCart(CartRequest.Cart request){
        User user = userService.getUserNotNonUser();
        ItemStock itemStock = itemStockRepository.findByItemNumber(request.getItemNumber());
        if(itemStock==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 상품 번호입니다.", request.getItemNumber()));
        }
        Cart cart = cartRepository.findByUserIdAndItemNumber(user.getId(), request.getItemNumber());
        if(cart!=null){
            cart.updateOne(itemStock.getQty());
            return;
        }
        Item item = itemStock.getItem();
        ItemOption itemOption = itemStock.getType().equals(EItemStockFlag.OPTION) ? itemOptionRepository.findByItemNumber(request.getItemNumber()) : null;
        int price = itemStock.getType().equals(EItemStockFlag.OPTION) ? itemOption.getPrice() : item.getPrice();
        Boolean isOrder = itemStock.getStatus().equals(EItemStatusFlag.SALE) && itemStock.getQty()>=1 ? true : false;
        cart = Cart.newInstance(user.getId(), request.getItemNumber(), price, 1, isOrder, request.getIsNow());
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCart(CartRequest.CartQty request){
        User user = userService.getUserNotNonUser();
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
        Cart cart = cartRepository.findByUserIdAndItemNumber(user.getId(), request.getItemNumber());
        if(cart==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 장바구니에 존재하지 않는 상품 번호입니다.", request.getItemNumber()));
        }
        cart.updateQty(request.getQty(), itemStock.getQty());
    }

    @Transactional
    public List<CartResponse> getCartList(){
        User user = userService.getUserNotNonUser();
        userService.validateNonMember(user);
        List<CartProjection> result = cartRepository.findAllByUserId(user.getId());
        return CartResponse.of(result);
    }


}
