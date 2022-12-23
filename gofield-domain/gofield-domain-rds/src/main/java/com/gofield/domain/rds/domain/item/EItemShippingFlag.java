package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemShippingFlag implements EnumCodeModel {
    SHIPPING("택배/소포/등기", "S"),
    DIRECT( "직접전달", "D");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
