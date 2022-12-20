package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelStatusFlag implements EnumCodeModel {
    ORDER_CANCEL_REQUEST("주문 취소 신청", "500"),
    ORDER_CANCEL_HOLD("주문 취소 보류", "501"),
    ORDER_CANCEL_PROCESS("주문 취소 처리중", "503"),
    ORDER_CANCEL_COMPLETE("주문 취소 완료", "504"),
    ORDER_CANCEL_DENIED("주문 거절/철회","505"),

    ORDER_CHANGE_REQUEST("주문 교환 신청", "600"),
    ORDER_CHANGE_HOLD("주문 교환 보류", "601"),
    ORDER_CHANGE_PROCESS("주문 교환 처리중", "603"),
    ORDER_CHANGE_COMPLETE("주문 교환 완료", "604"),
    ORDER_CHANGE_DENIED("주문 교환 거절/철회","605"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
