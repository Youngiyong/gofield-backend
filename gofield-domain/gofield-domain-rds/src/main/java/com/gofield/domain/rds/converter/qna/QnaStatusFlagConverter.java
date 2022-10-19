package com.gofield.domain.rds.converter.qna;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.qna.EQnaStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class QnaStatusFlagConverter extends AbstractEnumAttributeConverter<EQnaStatusFlag> {
    public static final String ENUM_NAME = "QNA 상태";

    public QnaStatusFlagConverter(){
        super(EQnaStatusFlag.class,false, ENUM_NAME);
    }
}
