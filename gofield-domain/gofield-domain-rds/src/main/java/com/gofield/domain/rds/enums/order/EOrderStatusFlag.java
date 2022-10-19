package com.gofield.domain.rds.enums.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderStatusFlag implements CodeEnum {
    ORDER_CREATE("주문 생성", "A"),
    ORDER_WAIT("주문 대기", "W"),
    ORDER_SHIPPING("주문 배송중", "S"),
    ORDER_CANCEL("주문 취소", "C"),
    ORDER_COMPLETE("주문 완료","O"),
    ORDER_SETTLEMENT("주문 정산 완료", "Z")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
