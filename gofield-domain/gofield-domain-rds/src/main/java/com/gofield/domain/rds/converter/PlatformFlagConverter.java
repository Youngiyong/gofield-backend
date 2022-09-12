package com.gofield.domain.rds.converter;

import com.gofield.domain.rds.enums.EPlatformFlag;

import javax.persistence.Converter;
@Converter(autoApply = true)
public class PlatformFlagConverter extends AbstractEnumAttributeConverter<EPlatformFlag>{
    public static final String ENUM_NAME = "모바일 플랫폼";

    public PlatformFlagConverter(){
        super(EPlatformFlag.class,false, ENUM_NAME);
    }
}
