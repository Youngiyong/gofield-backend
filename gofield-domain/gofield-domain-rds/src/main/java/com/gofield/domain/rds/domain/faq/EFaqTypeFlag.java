package com.gofield.domain.rds.domain.faq;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EFaqTypeFlag implements EnumCodeModel {
    LOGIN("로그인", "L"),
    ITEM("상품", "I"),
    ETC("기타", "E")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
