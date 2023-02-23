package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderItemStatusFlag implements EnumCodeModel {
    ORDER_ITEM_RECEIPT("주문완료", "400"),
    ORDER_ITEM_APPROVE("주문승인", "410"),
    ORDER_ITEM_RECEIPT_CANCEL("승인전 취소", "401"),
    ORDER_ITEM_APPROVE_CANCEL("승인후 취소", "411"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
