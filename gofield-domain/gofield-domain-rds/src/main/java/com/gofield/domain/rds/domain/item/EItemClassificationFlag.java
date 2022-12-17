package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemClassificationFlag implements EnumCodeModel {
    NEW("새상품", "N"),
    USED( "중고상품", "U");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
