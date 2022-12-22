package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderShippingStatusFlag implements EnumCodeModel {

    ORDER_SHIPPING_CHECK("상품확인중", "300"),
    ORDER_SHIPPING_CANCEL("주문취소접수", "301"),
    ORDER_SHIPPING_CANCEL_COMPLETE("주문취소완료", "302"),

    ORDER_SHIPPING_CHECK_COMPLETE("주문확인완료", "309"),
    ORDER_SHIPPING_READY("배송준비중", "310"),
    ORDER_SHIPPING_DELIVERY("배송중", "340"),
    ORDER_SHIPPING_DELIVERY_COMPLETE("배송완료", "350"),
    ORDER_SHIPPING_COMPLETE("구매확정","360"),

    ORDER_SHIPPING_CHANGE("교환진행중", "371"),
    ORDER_SHIPPING_CHANGE_COMPLETE("교환완료", "372"),
    ORDER_SHIPPING_RETURN("반품진행중", "381"),
    ORDER_SHIPPING_RETURN_COMPLETE("반품완료", "382"),
    ORDER_SHIPPING_DELETE("주문내역삭제", "400"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
