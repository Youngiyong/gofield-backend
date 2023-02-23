package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemClassificationFlagConverter extends AbstractEnumAttributeConverter<EItemClassificationFlag> {
    public static final String ENUM_NAME = "상품 분류";

    public ItemClassificationFlagConverter(){
        super(EItemClassificationFlag.class,false, ENUM_NAME);
    }
}
