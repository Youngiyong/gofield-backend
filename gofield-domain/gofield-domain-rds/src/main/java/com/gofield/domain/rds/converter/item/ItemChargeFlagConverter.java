package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemChargeFlagConverter extends AbstractEnumAttributeConverter<EItemChargeFlag> {
    public static final String ENUM_NAME = "배송비 구분 타입";

    public ItemChargeFlagConverter(){
        super(EItemChargeFlag.class,false, ENUM_NAME);
    }
}
