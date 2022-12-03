package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.order.EOrderItemStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderItemStatusFlagConverter extends AbstractEnumAttributeConverter<EOrderItemStatusFlag> {
    public static final String ENUM_NAME = "주문 상품 상태";

    public OrderItemStatusFlagConverter(){
        super(EOrderItemStatusFlag.class,false, ENUM_NAME);
    }
}
