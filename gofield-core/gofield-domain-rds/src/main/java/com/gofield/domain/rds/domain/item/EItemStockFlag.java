package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemStockFlag implements EnumCodeModel {
    COMMON("일반 상품", "C"),
    OPTION("옵션 상품", "O")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
