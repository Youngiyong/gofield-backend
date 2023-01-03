package com.gofield.admin.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemEditDto {

    private ItemDto itemDto;
    private List<ItemImageDto> images;

    @Builder
    private ItemEditDto(ItemDto itemDto, List<ItemImageDto> images){
        this.itemDto = itemDto;
        this.images = images;
    }

    public static ItemEditDto of(ItemDto itemDto, List<ItemImageDto> images){
        return ItemEditDto.builder()
                .itemDto(itemDto)
                .images(images)
                .build();
    }
}
