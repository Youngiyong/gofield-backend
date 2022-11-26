package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemGoodsFlag implements CodeEnum {
    DISCOUNT("할인상품", "D"),
    NEW_PRICE("정가상품", "N"),
    OPTION("옵션상품", "O")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
