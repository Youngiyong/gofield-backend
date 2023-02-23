package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCancelReasonFlagConverter extends AbstractEnumAttributeConverter<EOrderCancelReasonFlag> {
    public static final String ENUM_NAME = "주문 취소 이유";

    public OrderCancelReasonFlagConverter(){
        super(EOrderCancelReasonFlag.class,false, ENUM_NAME);
    }
}
