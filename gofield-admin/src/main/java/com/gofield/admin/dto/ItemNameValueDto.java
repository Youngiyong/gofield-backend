package com.gofield.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemNameValueDto {
    private String name;
    private String value;

    @Builder
    private ItemNameValueDto(String name, String value){
        this.name = name;
        this.value = value;
    }

    public static ItemNameValueDto of(String name, String value){
        return ItemNameValueDto.builder()
                .name(name)
                .value(value)
                .build();
    }
}
