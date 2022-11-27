package com.gofield.domain.rds.converter;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.EItemStockFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemStockFlagConverter extends AbstractEnumAttributeConverter<EItemStockFlag> {
    public static final String ENUM_NAME = "상품 타입";

    public ItemStockFlagConverter(){
        super(EItemStockFlag.class,false, ENUM_NAME);
    }
}
