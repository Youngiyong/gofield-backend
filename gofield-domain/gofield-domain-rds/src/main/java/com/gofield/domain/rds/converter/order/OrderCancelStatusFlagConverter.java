package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCancelStatusFlagConverter extends AbstractEnumAttributeConverter<EOrderCancelStatusFlag> {
    public static final String ENUM_NAME = "주문 취소 상태";

    public OrderCancelStatusFlagConverter(){
        super(EOrderCancelStatusFlag.class,false, ENUM_NAME);
    }
}
