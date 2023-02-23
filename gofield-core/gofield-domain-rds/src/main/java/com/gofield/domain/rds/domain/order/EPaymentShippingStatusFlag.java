package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EPaymentShippingStatusFlag implements EnumCodeModel {
    PAYMENT_CREATE("결제생성", "200"),
    PAYMENT_CANCEL("결제취소", "201"),
    PAYMENT_COMPLETE("결제완료","210"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
