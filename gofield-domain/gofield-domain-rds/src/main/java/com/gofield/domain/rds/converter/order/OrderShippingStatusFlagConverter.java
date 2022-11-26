package com.gofield.domain.rds.converter.order;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.enums.order.EOrderStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderShippingStatusFlagConverter extends AbstractEnumAttributeConverter<EOrderShippingStatusFlag> {
    public static final String ENUM_NAME = "주문 배송 상태";

    public OrderShippingStatusFlagConverter(){
        super(EOrderShippingStatusFlag.class,false, ENUM_NAME);
    }
}
