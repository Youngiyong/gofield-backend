package com.gofield.domain.rds.converter.faq;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.faq.EFaqTypeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class FaqTypeFlagConverter extends AbstractEnumAttributeConverter<EFaqTypeFlag> {
    public static final String ENUM_NAME = "FAQ 타입";

    public FaqTypeFlagConverter(){
        super(EFaqTypeFlag.class,false, ENUM_NAME);
    }
}
