package com.gofield.domain.rds.converter;


import com.gofield.domain.rds.enums.EStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusFlagConverter extends AbstractEnumAttributeConverter<EStatusFlag>{
    public static final String ENUM_NAME = "상태 코드";

    public StatusFlagConverter(){
        super(EStatusFlag.class,false, ENUM_NAME);
    }
}

