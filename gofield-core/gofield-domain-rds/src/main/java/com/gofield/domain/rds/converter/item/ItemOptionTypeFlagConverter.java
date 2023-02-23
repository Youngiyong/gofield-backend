package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemOptionTypeFlagConverter extends AbstractEnumAttributeConverter<EItemOptionTypeFlag> {
    public static final String ENUM_NAME = "상품 옵션";

    public ItemOptionTypeFlagConverter(){
        super(EItemOptionTypeFlag.class,false, ENUM_NAME);
    }
}
