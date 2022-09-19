package com.gofield.domain.rds.converter;

import com.gofield.common.exception.ConvertException;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class AbstractEnumAttributeConverter<E extends Enum<E> & CodeEnum> implements AttributeConverter<E, String> {
    private Class<E> targetEnumClass;
    private boolean nullable;
    private String enumName;

    protected AbstractEnumAttributeConverter(Class<E> E, boolean nullable, String enumName){
        this.targetEnumClass = E;
        this.nullable = nullable;
        this.enumName = enumName;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        if (!nullable && attribute == null)
            throw new ConvertException(String.format("%s 는 NULL로 지정할 수 없습니다.", enumName));

        return EnumValuesConverter.toDBcode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData){
        if (!nullable && StringUtils.isEmpty(dbData))
            throw new ConvertException(String.format("%s 가 DB에 NULL 혹은 empty(%s)로 저장 되어 있습니다.", enumName,dbData));

        return EnumValuesConverter.ofDBCode(targetEnumClass,dbData);
    }
}
