package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.ItemBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
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

    @Builder
    private ItemDto(Long id, String name, List<CategoryDto> categoryList, List<BrandDto> brandList, Long categoryId, String categoryName, Long brandId, String brandName, String thumbnail, Boolean isRecommend, String createDate){
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
    }

    public static ItemDto of(List<CategoryDto> categoryList, List<BrandDto> brandList){
        return ItemDto.builder()
                .categoryList(categoryList)
                .brandList(brandList)
                .build();
    }

    public static ItemDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, ItemBundle itemBundle){
        return ItemDto.builder()
                .id(itemBundle.getId())
                .name(itemBundle.getName())
                .categoryList(categoryList)
                .brandList(brandList)
                .categoryId(itemBundle.getCategory().getId())
                .categoryName(itemBundle.getCategory().getName())
                .brandId(itemBundle.getBrand().getId())
                .brandName(itemBundle.getBrand().getName())
                .isRecommend(itemBundle.getIsRecommend())
                .thumbnail(itemBundle.getThumbnail()==null ? null : Constants.CDN_URL.concat(itemBundle.getThumbnail()).concat(Constants.RESIZE_150x150))
                .build();
    }
}
