package com.gofield.api.service;

import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.CartResponse;
import com.gofield.api.dto.res.CountResponse;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.cart.Cart;
import com.gofield.domain.rds.domain.cart.CartRepository;
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
    private final CartRepository cartRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public CountResponse getCartCount(){
        User user = userService.getUser();
        userService.validateNonMember(user);
        return CountResponse.of(cartRepository.getCartCount(user.getId()));
    }

    @Transactional
    public void insertCart(ItemRequest.Cart request){
        User user = userService.getUser();
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
        Boolean isOrder = itemStock.getStatus().equals(EItemStatusFlag.SALE) && itemStock.getQty()>= request.getQty() ? true : false;
        cart = Cart.newInstance(user.getId(), itemStock.getItem().getId(), request.getItemNumber(), price, request.getQty(), isOrder, request.getIsNow());
        cartRepository.save(cart);
    }

    @Transactional
    public List<CartResponse> getCartList(){
        User user = userService.getUser();
        userService.validateNonMember(user);
        List<CartProjection> result = cartRepository.findAllByUserId(user.getId());
        return CartResponse.of(result);
    }
}
