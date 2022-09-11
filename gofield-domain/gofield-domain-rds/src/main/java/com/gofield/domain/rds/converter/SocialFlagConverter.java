package com.gofield.domain.rds.converter;


import com.gofield.common.exception.model.ConvertException;
import com.gofield.common.exception.type.ErrorAction;
import com.gofield.common.exception.type.ErrorCode;
import com.gofield.domain.rds.enums.ESocialFlag;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SocialFlagConverter implements AttributeConverter<ESocialFlag, String> {

    @Override
    public String convertToDatabaseColumn(ESocialFlag attribute) {
        if(attribute==null){
            throw new ConvertException(ErrorCode.E470_CONVERT_EXCEPTION, ErrorAction.NONE, String.format("flag는 NULL로 저장할 수 없습니다."));
        }
        return attribute.getCode();
    }

    @Override
    public ESocialFlag convertToEntityAttribute(String dbData) {
        if(dbData==null && dbData.isBlank()){
            throw new ConvertException(ErrorCode.E470_CONVERT_EXCEPTION, ErrorAction.NONE, String.format("%s(이) DB에 NULL 혹은 빈값으로 저장 되어 있습니다.", dbData));
        }

        return ESocialFlag.ofCode(dbData);
    }
}
