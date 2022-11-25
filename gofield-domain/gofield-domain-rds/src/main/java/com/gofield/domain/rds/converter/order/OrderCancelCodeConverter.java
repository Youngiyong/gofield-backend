package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.order.EOrderCancelStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderCancelCodeConverter extends AbstractEnumAttributeConverter<EOrderCancelStatusFlag> {
    public static final String ENUM_NAME = "주문 취소 요청 코드";

    public OrderCancelCodeConverter(){
        super(EOrderCancelStatusFlag.class,false, ENUM_NAME);
    }
}
