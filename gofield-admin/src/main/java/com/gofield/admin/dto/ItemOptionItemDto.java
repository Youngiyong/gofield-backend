package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ItemOptionItemDto {
    private List<String> values;
    private Integer price;
    private Integer qty;
    private EItemStatusFlag status;

    @Builder
    private ItemOptionItemDto(List<String> values, Integer price, Integer qty, EItemStatusFlag status){
        this.values = values;
        this.price = price;
        this.qty = qty;
        this.status = status;
    }

    public static ItemOptionItemDto of(List<String> values, Integer price, Integer qty, EItemStatusFlag status){
        return ItemOptionItemDto.builder()
                .values(values)
                .price(price)
                .qty(qty)
                .status(status)
                .build();
    }

    public static List<ItemOptionItemDto> of(List<Map<String, Object>> optionItemList){
        return optionItemList.stream()
                .map(p -> ItemOptionItemDto.of((List<String>) p.get("values"), Integer.parseInt(String.valueOf(p.get("price"))), Integer.parseInt(String.valueOf( p.get("qty"))), EItemStatusFlag.valueOf(String.valueOf(p.get("status")))))
                .collect(Collectors.toList());
    }
}
