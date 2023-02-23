package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.order.EOrderCancelItemFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelTypeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCancelItemFlagConverter extends AbstractEnumAttributeConverter<EOrderCancelItemFlag> {
    public static final String ENUM_NAME = "주문 취소 상품 타입";

    public OrderCancelItemFlagConverter(){
        super(EOrderCancelItemFlag.class,false, ENUM_NAME);
    }
}
