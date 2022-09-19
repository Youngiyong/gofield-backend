package com.gofield.domain.rds.converter.banner;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.gofield.domain.rds.enums.banner.EBannerTypeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class BannerTypeFlagConverter extends AbstractEnumAttributeConverter<EBannerTypeFlag> {
    public static final String ENUM_NAME = "배너 타입";

    public BannerTypeFlagConverter(){
        super(EBannerTypeFlag.class,false, ENUM_NAME);
    }
}
