package com.gofield.domain.rds.converter;


import com.gofield.domain.rds.domain.item.EItemPickupFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemPickupFlagConverter extends AbstractEnumAttributeConverter<EItemPickupFlag> {
    public static final String ENUM_NAME = "배송방법";

    public ItemPickupFlagConverter(){
        super(EItemPickupFlag.class,false, ENUM_NAME);
    }
}

