package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelItemFlag implements CodeEnum {
    ORDER_ITEM("상품", "101"),
    ORDER_ITEM_OPTION("상품 옵션", "102"),
    SELLER("셀러 취소", "103"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
