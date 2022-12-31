package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
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
}
