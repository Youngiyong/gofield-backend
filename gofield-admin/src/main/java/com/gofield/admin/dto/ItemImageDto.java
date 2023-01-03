package com.gofield.admin.dto;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.ItemBundleImage;
import com.gofield.domain.rds.domain.item.ItemImage;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemImageDto {
    private Long id;
    private String image;
    private String createDate;


    @Builder
    private ItemImageDto(Long id, String image, String createDate){
        this.id = id;
        this.image = image;
        this.createDate = createDate;
    }

    public static ItemImageDto of(ItemImage itemImage){
        return ItemImageDto.builder()
                .id(itemImage.getId())
                .image(CommonUtils.makeCloudFrontAdminUrl(itemImage.getImage()))
                .createDate(itemImage.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<ItemImageDto> of(List<ItemImage> list){
        return list.stream()
                .map(p -> ItemImageDto.of(p))
                .collect(Collectors.toList());
    }

}
