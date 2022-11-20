package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.item.EItemStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemStatusFlagConverter extends AbstractEnumAttributeConverter<EItemStatusFlag> {
    public static final String ENUM_NAME = "상품 재고 상태";

    public ItemStatusFlagConverter(){
        super(EItemStatusFlag.class,false, ENUM_NAME);
    }
}
