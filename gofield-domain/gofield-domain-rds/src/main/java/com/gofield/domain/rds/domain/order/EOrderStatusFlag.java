package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderStatusFlag implements CodeEnum {
    ORDER_CREATE("주문 생성", "200"),
    ORDER_CANCEL("주문 취소", "201"),
    ORDER_APPROVAL("주문 승인", "203"),
    ORDER_COMPLETE("주문 완료","210"),
    ORDER_DELETE("주문 삭제", "299"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
