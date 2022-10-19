package com.gofield.domain.rds.converter.item;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.enums.item.EDeliveryFlag;
import com.gofield.domain.rds.enums.qna.EQnaStatusFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class DeliveryFlagConverter extends AbstractEnumAttributeConverter<EDeliveryFlag> {
    public static final String ENUM_NAME = "배송 타입";

    public DeliveryFlagConverter(){
        super(EDeliveryFlag.class,false, ENUM_NAME);
    }
}
