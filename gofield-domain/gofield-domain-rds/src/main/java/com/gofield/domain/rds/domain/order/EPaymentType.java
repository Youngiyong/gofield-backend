package com.gofield.domain.rds.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum EPaymentType {
    CARD, EASYPAY, BANK
    ;
}
