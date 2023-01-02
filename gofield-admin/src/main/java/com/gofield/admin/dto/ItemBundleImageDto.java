package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.ItemBundleImage;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemBundleImageDto {
    private Long id;
    private String image;
    private String createDate;


    @Builder
    private ItemBundleImageDto(Long id, String image, String createDate){
        this.id = id;
        this.image = image;
        this.createDate = createDate;
    }

    public static ItemBundleImageDto of(ItemBundleImage itemBundleImage){
        return ItemBundleImageDto.builder()
                .id(itemBundleImage.getId())
                .image(CommonUtils.makeCloudFrontAdminUrl(itemBundleImage.getImage()))
                .createDate(itemBundleImage.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<ItemBundleImageDto> of(List<ItemBundleImage> list){
        return list.stream()
                .map(p -> ItemBundleImageDto.of(p))
                .collect(Collectors.toList());
    }

}
