package com.gofield.domain.rds.domain.banner;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EBannerTypeFlag implements EnumCodeModel {

    TOP("상단", "T"),
    MIDDLE("중앙", "M"),
    LOWER("하단", "L")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
