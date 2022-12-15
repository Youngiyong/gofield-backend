package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.Category;
import com.gofield.domain.rds.domain.item.ItemBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemBundleDto {
    private Long id;
    private String name;
    private List<CategoryDto> categoryList;
    private List<BrandDto> brandList;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private String thumbnail;
    private Boolean isActive;
    private Boolean isRecommend;
    private String createDate;

    @Builder
    private ItemBundleDto(Long id, String name, List<CategoryDto> categoryList, List<BrandDto> brandList, Long categoryId, String categoryName, Long brandId, String brandName, String thumbnail, Boolean isActive, Boolean isRecommend, String createDate){
        this.id = id;
        this.name = name;
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.isActive = isActive;
        this.isRecommend = isRecommend;
        this.createDate = createDate;
    }

    public static ItemBundleDto of(ItemBundle itemBundle, List<CategoryDto> categoryList, List<BrandDto> brandList, String categoryName, String brandName){
        return ItemBundleDto.builder()
                .id(itemBundle.getId())
                .name(itemBundle.getName())
                .categoryList(categoryList)
                .brandList(brandList)
                .categoryId(itemBundle.getCategory().getId())
                .categoryName(categoryName)
                .brandId(itemBundle.getBrand().getId())
                .brandName(brandName)
                .thumbnail(itemBundle.getThumbnail())
                .isActive(itemBundle.getIsActive())
                .isRecommend(itemBundle.getIsRecommend())
                .createDate(itemBundle.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static ItemBundleDto of(List<CategoryDto> categoryList, List<BrandDto> brandList){
        return ItemBundleDto.builder()
                .categoryList(categoryList)
                .brandList(brandList)
                .build();
    }
}
