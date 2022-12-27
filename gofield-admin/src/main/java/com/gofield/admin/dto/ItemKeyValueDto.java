package com.gofield.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemKeyValueDto {
    private String key;
    private String value;

    @Builder
    public ItemKeyValueDto(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static ItemKeyValueDto of(String key, String value){
        return ItemKeyValueDto.builder()
                .key(key)
                .value(value)
                .build();
    }
}
