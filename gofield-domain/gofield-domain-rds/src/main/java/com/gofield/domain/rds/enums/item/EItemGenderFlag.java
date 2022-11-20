package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemGenderFlag implements CodeEnum {
    FEMALE("여성", "F"),
    MALE( "남성", "M"),
    CHILDREN("어린이", "C"),
    ETC("기타", "N")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
