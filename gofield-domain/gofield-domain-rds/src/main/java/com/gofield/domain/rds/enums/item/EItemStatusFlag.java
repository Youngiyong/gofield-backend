package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemStatusFlag implements CodeEnum {
    SOLD_OUT("품절", "W"),
    SALE("판매중", "A")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
