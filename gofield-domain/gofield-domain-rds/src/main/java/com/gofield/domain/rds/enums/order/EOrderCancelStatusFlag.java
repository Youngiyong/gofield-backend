package com.gofield.domain.rds.enums.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelStatusFlag implements CodeEnum {
    ORDER_CANCEL_REQUEST("주문 취소 신청", "500"),
    ORDER_CANCEL_HOLD("주문 취소 보류", "501"),
    ORDER_CANCEL_PROCESS("주문 취소 처리중", "503"),
    ORDER_CANCEL_COMPLETE("주문 취소 완료", "504"),
    ORDER_CANCEL_DENIED("주문 거절/ㅈ철회","505"),
    ;
    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
