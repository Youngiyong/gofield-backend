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
    private Long id;
    private String name;
    private List<CategoryDto> categoryList;
    private List<BrandDto> brandList;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private String thumbnail;
    private Boolean isRecommend;
    private String createDate;

    private List<ItemBundleImageDto> images;

    @Builder
    private ItemBundleEditDto(Long id, String name, List<CategoryDto> categoryList, List<BrandDto> brandList, Long categoryId, String categoryName, Long brandId, String brandName, String thumbnail, Boolean isRecommend, String createDate, List<ItemBundleImageDto> images){
        this.id = id;
        this.name = name;
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.isRecommend = isRecommend;
        this.createDate = createDate;
        this.images = images;
    }

    public static ItemBundleEditDto of(ItemBundle itemBundle, List<CategoryDto> categoryList, List<BrandDto> brandList){
        List<ItemBundleImage> imageList = itemBundle.getImages();
        List<ItemBundleImageDto> imageDtoList = ItemBundleImageDto.of(imageList);
        return ItemBundleEditDto.builder()
                .id(itemBundle.getId())
                .name(itemBundle.getName())
                .categoryId(itemBundle.getCategory().getId())
                .brandId(itemBundle.getBrand().getId())
                .brandName(itemBundle.getBrand().getName())
                .thumbnail(Constants.CDN_URL.concat(itemBundle.getThumbnail()).concat(Constants.RESIZE_150x150))
                .isRecommend(itemBundle.getIsRecommend())
                .createDate(itemBundle.getCreateDate().toLocalDate().toString())
                .categoryList(categoryList)
                .brandList(brandList)
                .images(imageDtoList)
                .build();
    }
}
