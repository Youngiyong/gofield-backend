package com.gofield.domain.rds.converter.qna;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.item.EItemQnaStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class QnaStatusFlagConverter extends AbstractEnumAttributeConverter<EItemQnaStatusFlag> {
    public static final String ENUM_NAME = "QNA 상태";

    public QnaStatusFlagConverter(){
        super(EItemQnaStatusFlag.class,false, ENUM_NAME);
    }
}
