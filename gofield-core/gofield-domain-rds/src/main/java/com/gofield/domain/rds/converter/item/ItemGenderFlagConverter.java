package com.gofield.domain.rds.converter.item;


import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemGenderFlagConverter extends AbstractEnumAttributeConverter<EItemGenderFlag> {
    public static final String ENUM_NAME = "성별 코드";

    public ItemGenderFlagConverter(){
        super(EItemGenderFlag.class,false, ENUM_NAME);
    }
}

