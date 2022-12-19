package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import com.gofield.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelClientReason implements EnumModel {
    CANCEL_REASON_101("상품이 마음에 들지 않음(단숨변심)"),
    CANCEL_REASON_102("상품이 문제 있음(불량)"),
    CANCEL_REASON_103("다른 상품 추가 후 재주문 예정"),
    CANCEL_REASON_104("오배송 및 배송 누락"),
    ;

    private String description;

    @Override
    public String getKey() {
        return name();
    }
}
