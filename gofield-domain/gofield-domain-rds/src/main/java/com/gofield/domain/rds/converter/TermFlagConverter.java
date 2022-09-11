package com.gofield.domain.rds.converter;



import com.gofield.domain.rds.enums.ETermFlag;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class TermFlagConverter implements AttributeConverter<ETermFlag, String> {

    @Override
    public String convertToDatabaseColumn(ETermFlag attribute) {
        return attribute.getCode();
    }

    @Override
    public ETermFlag convertToEntityAttribute(String dbData) {
        return ETermFlag.ofCode(dbData);
    }

}