package com.gofield.admin.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.admin.util.AdminUtil;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ItemOptionItemDto {
    private List<String> values;
    private String itemNumber;
    private Integer price;
    private Integer qty;
    private EItemStatusFlag status;

    @Builder
    private ItemOptionItemDto(List<String> values, String itemNumber, Integer price, Integer qty, EItemStatusFlag status){
        this.values = values;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.status = status;
    }

    public static ItemOptionItemDto of(List<String> values, String itemNumber, Integer price, Integer qty, EItemStatusFlag status){
        return ItemOptionItemDto.builder()
                .values(values)
                .itemNumber(itemNumber)
                .price(price)
                .qty(qty)
                .status(status)
                .build();
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

    public static List<ItemOptionItemDto> ofEdit(List<Map<String, Object>> optionItemList){
        return optionItemList.stream()
                .map(p -> ItemOptionItemDto.of((List<String>) p.get("values"), String.valueOf(p.get("itemNumber")), Integer.parseInt(String.valueOf(p.get("price"))), Integer.parseInt(String.valueOf( p.get("qty"))), EItemStatusFlag.valueOf(String.valueOf(p.get("status")))))
                .collect(Collectors.toList());
    }

    public static List<ItemOptionItemDto> ofList(List<ItemOptionProjection> optionList){
        return optionList.stream()
                .map(p -> ItemOptionItemDto.of(p.getOptionName()==null ? null : AdminUtil.strToObject(p.getName(), new TypeReference<List<String>>(){}),
                        p.getItemNumber(), p.getOptionPrice(), p.getQty(), p.getStatus()))
                .collect(Collectors.toList());
    }
}
