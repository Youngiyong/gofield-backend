package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import com.gofield.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EPaymentType implements EnumModel {
    CARD("카드"), EASYPAY("간편결제"), VIRTUAL_ACCOUNT("가상계좌");

    private String description;

    @Override
    public String getKey() {
        return name();
    }



}
