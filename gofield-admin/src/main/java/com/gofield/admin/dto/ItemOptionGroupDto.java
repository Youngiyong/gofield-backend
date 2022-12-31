package com.gofield.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemOptionGroupDto {
    private String name;
    private String value;

    @Builder
    private ItemOptionGroupDto(String name, String value){
        this.name = name;
        this.value = value;
    }

    public static ItemOptionGroupDto of(String name, String value){
        return ItemOptionGroupDto.builder()
                .name(name)
                .value(value)
                .build();
    }

}
