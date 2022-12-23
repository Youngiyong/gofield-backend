package com.gofield.domain.rds.converter.item;


import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemShippingFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemShippingFlagConverter extends AbstractEnumAttributeConverter<EItemShippingFlag> {
    public static final String ENUM_NAME = "상품 배송 방법";

    public ItemShippingFlagConverter(){
        super(EItemShippingFlag.class,false, ENUM_NAME);
    }
}

