package com.gofield.domain.rds.converter;

import com.gofield.domain.rds.enums.ETermFlag;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TermFlagConverter extends AbstractEnumAttributeConverter<ETermFlag>{
    public static final String ENUM_NAME = "약관 타입";

    public TermFlagConverter(){
        super(ETermFlag.class,false, ENUM_NAME);
    }
}

