package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemClassificationFlag implements CodeEnum {
    NEW("새상품", "N"),
    USED( "중고상품", "U");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
