package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.item.EItemGoodsFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemGoodsFlagConverter extends AbstractEnumAttributeConverter<EItemGoodsFlag> {
    public static final String ENUM_NAME = "상품 타입";

    public ItemGoodsFlagConverter(){
        super(EItemGoodsFlag.class,false, ENUM_NAME);
    }
}
