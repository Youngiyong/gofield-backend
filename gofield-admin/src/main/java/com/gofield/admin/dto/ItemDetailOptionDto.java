package com.gofield.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDetailOptionDto {

    private String key;
    private String value;

    @Builder
    private ItemDetailOptionDto(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static ItemDetailOptionDto of(String key, String value){
        return ItemDetailOptionDto.builder()
                .key(key)
                .value(value)
                .build();
    }
}