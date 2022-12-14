package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemStatusFlag implements EnumCodeModel {
    SOLD_OUT("품절", "W"),
    HIDE("숨김", "H"),
    SALE("판매중", "A")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
