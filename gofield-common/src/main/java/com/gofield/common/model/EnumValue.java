package com.gofield.common.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class EnumValue {

    private final String key;
    private final String description;

    private EnumValue(EnumCodeModel enumModel) {
        key = enumModel.getKey();
        description = enumModel.getDescription();
    }

    private EnumValue(EnumModel enumModel) {
        key = enumModel.getKey();
        description = enumModel.getDescription();
    }

    public static EnumValue of(EnumCodeModel enumModel) {
        return new EnumValue(enumModel);
    }

    public static EnumValue of(EnumModel enumModel) {
        return new EnumValue(enumModel);
    }


}