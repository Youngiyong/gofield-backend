package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.order.EOrderCancelCodeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCancelCoeFlaglConverter extends AbstractEnumAttributeConverter<EOrderCancelCodeFlag> {
    public static final String ENUM_NAME = "주문 취소 요청 코드";

    public OrderCancelCoeFlaglConverter(){
        super(EOrderCancelCodeFlag.class,false, ENUM_NAME);
    }
}
