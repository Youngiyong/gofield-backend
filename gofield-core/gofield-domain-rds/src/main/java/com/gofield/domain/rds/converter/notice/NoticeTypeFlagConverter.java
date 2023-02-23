package com.gofield.domain.rds.converter.notice;

import com.gofield.domain.rds.converter.AbstractEnumAttributeConverter;
import com.gofield.domain.rds.domain.faq.EFaqTypeFlag;
import com.gofield.domain.rds.domain.notice.ENoticeTypeFlag;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class NoticeTypeFlagConverter extends AbstractEnumAttributeConverter<ENoticeTypeFlag> {
    public static final String ENUM_NAME = "공지사항 타입";

    public NoticeTypeFlagConverter(){
        super(ENoticeTypeFlag.class,false, ENUM_NAME);
    }
}
