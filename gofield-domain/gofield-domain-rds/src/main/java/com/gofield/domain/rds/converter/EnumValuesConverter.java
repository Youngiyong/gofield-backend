package com.gofield.domain.rds.converter;

import com.gofield.common.exception.ConvertException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumValuesConverter {
    public static <T extends Enum<T> & CodeEnum> T ofDBCode(Class<T> enumClass, String dbCode){
        if(dbCode.isEmpty()){
            return null;
        }

        return EnumSet.allOf(enumClass).stream()
                .filter(v -> v.getCode().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new ConvertException(String.format("enum=[%s], code=[%s]가 존재하지 않습니다.",enumClass.getName(),dbCode)));
    }

    public static <T extends Enum<T> & CodeEnum> String toDBcode(T enumValue) {
        return enumValue.getCode();
    }
}
