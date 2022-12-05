package com.gofield.domain.rds.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ECodeGroup {
    BANK,
    CARRIER,
    SHIPPING_COMMENT,
    PAYMENT_CARD,
    PAYMENT_EASYPAY,
    PAYMENT_METHOD
    ;
}
