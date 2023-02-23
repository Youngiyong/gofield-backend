package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.order.EOrderCancelTypeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCancelTypeFlagConverter extends AbstractEnumAttributeConverter<EOrderCancelTypeFlag> {
    public static final String ENUM_NAME = "주문 취소 타입";

    public OrderCancelTypeFlagConverter(){
        super(EOrderCancelTypeFlag.class,false, ENUM_NAME);
    }
}
