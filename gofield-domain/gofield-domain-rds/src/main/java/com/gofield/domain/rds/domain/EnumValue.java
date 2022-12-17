package com.gofield.domain.rds.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class EnumValue {
    private String code;
    private String name;

    @Builder
    private EnumValue(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static EnumValue of(String code ,String name){
        return EnumValue.builder()
                .code(code)
                .name(name)
                .build();
    }
}
