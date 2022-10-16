package com.gofield.domain.rds.converter;

import com.gofield.domain.rds.enums.EEnvironmentFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnvironmentFlagConverter extends AbstractEnumAttributeConverter<EEnvironmentFlag>{
    public static final String ENUM_NAME = "개발 환경 타입";

    public EnvironmentFlagConverter(){
        super(EEnvironmentFlag.class,false, ENUM_NAME);
    }
}

