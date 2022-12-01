package com.gofield.api.dto.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum PaymentType {
    CREDIT_CART, DEPOSIT_WITHOUT_TRANSFER, BANK_TRANSFER
    ;
}
