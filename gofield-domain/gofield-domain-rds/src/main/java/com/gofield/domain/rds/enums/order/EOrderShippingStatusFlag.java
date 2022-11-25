package com.gofield.domain.rds.enums.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderShippingStatusFlag implements CodeEnum {

    ORDER_SHIPPING_CHECK("상품확인중", "300"),
    ORDER_SHIPPING_CANCEL("주문 취소", "301"),
    ORDER_SHIPPING_CHECK_COMPLETE("주문 확인 완료", "309"),
    ORDER_SHIPPING_READY("상품 준비중", "310"),
    ORDER_SHIPPING_DELIVERY("배송중", "340"),
    ORDER_SHIPPING_DELIVERY_COMPLETE("인계완료", "350"),
    ORDER_SHIPPING_COMPLETE("구매 확정","360"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
