package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemDeliveryFlagConverter extends AbstractEnumAttributeConverter<EItemDeliveryFlag> {
    public static final String ENUM_NAME = "배송 타입";

    public ItemDeliveryFlagConverter(){
        super(EItemDeliveryFlag.class,false, ENUM_NAME);
    }
}
