package com.gofield.domain.rds.converter;

import com.gofield.domain.rds.domain.item.ECategoryFlag;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CategoryFlagConverter extends AbstractEnumAttributeConverter<ECategoryFlag>{
    public static final String ENUM_NAME = "카테고리 플랫폼";

    public CategoryFlagConverter(){
        super(ECategoryFlag.class,false, ENUM_NAME);
    }
}
