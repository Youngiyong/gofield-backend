package com.gofield.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemNamePriceDto {
    private String name;
    private int price;

    @Builder
    private ItemNamePriceDto(String name, int price){
        this.name = name;
        this.price = price;
    }

    public static ItemNamePriceDto of(String name, int price){
        return ItemNamePriceDto.builder()
                .name(name)
                .price(price)
                .build();
    }
}
