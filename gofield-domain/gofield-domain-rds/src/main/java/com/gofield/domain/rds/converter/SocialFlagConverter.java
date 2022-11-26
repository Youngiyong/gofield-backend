package com.gofield.domain.rds.converter;


import com.gofield.domain.rds.domain.user.ESocialFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class SocialFlagConverter extends AbstractEnumAttributeConverter<ESocialFlag>{
    public static final String ENUM_NAME = "소셜 타입";

    public SocialFlagConverter(){
        super(ESocialFlag.class,false, ENUM_NAME);
    }
}

