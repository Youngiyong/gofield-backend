package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemDeliveryFlag implements CodeEnum {
    FREE("무료배송", "F"),
    PAY( "유료배송", "P");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
