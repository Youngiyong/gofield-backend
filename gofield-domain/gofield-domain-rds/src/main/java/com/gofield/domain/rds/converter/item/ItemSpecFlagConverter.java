package com.gofield.domain.rds.converter.item;


import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.item.EItemSpecFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemSpecFlagConverter extends AbstractEnumAttributeConverter<EItemSpecFlag> {
    public static final String ENUM_NAME = "스펙 코드";

    public ItemSpecFlagConverter(){
        super(EItemSpecFlag.class,false, ENUM_NAME);
    }
}

