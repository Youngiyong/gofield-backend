package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EDeliveryFlag implements CodeEnum {
    BUNDLE("묶음 배송", "B"),
    EACH( "개별 배송", "E");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
