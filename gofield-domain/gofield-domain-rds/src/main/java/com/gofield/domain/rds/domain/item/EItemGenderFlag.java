package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemGenderFlag implements EnumCodeModel {
    FEMALE("여성용", "F"),
    MALE( "남성용", "M"),
    CHILDREN("어린이용", "C"),
    UNISEX("남녀공용", "U"),
    ETC("기타", "E"),
    NONE("선택없음", "N")

    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
