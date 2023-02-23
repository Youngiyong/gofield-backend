package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemDeliveryFlag implements EnumCodeModel {
    FREE("무료배송", "F"),
    CONDITION("조건부배송","C"),
    PAY( "유료배송", "P");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
