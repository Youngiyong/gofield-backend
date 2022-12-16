package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.ItemBundle;
import com.gofield.domain.rds.domain.item.ItemBundleImage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemBundleEditDto {

    private ItemBundleDto itemBundleDto;
    private List<ItemBundleImageDto> images;

    @Builder
    private ItemBundleEditDto(ItemBundleDto itemBundleDto, List<ItemBundleImageDto> images){
        this.itemBundleDto = itemBundleDto;
        this.images = images;
    }

    public static ItemBundleEditDto of(ItemBundleDto itemBundleDto, List<ItemBundleImageDto> images){
        return ItemBundleEditDto.builder()
                .itemBundleDto(itemBundleDto)
                .images(images)
                .build();
    }
}
