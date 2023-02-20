package com.gofield.admin.dto;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.ItemBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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
    private Boolean isRecommend;
    private String description;
    private String createDate;

    @Builder
    private ItemBundleDto(Long id, String name, List<CategoryDto> categoryList, List<BrandDto> brandList, Long categoryId, String categoryName, Long brandId, String brandName, String thumbnail,  Boolean isRecommend, String description, String createDate) {
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
        this.description = description;
        this.createDate = createDate;
    }

    public static ItemBundleDto of(List<CategoryDto> categoryList, List<BrandDto> brandList){
        return ItemBundleDto.builder()
                .categoryList(categoryList)
                .brandList(brandList)
                .build();
    }

    public static ItemBundleDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, ItemBundle itemBundle){
        return ItemBundleDto.builder()
                .id(itemBundle.getId())
                .name(itemBundle.getName())
                .categoryList(categoryList)
                .brandList(brandList)
                .categoryId(itemBundle.getCategory().getId())
                .categoryName(itemBundle.getCategory().getName())
                .brandId(itemBundle.getBrand().getId())
                .brandName(itemBundle.getBrand().getName())
                .isRecommend(itemBundle.getIsRecommend())
                .description(itemBundle.getDescription())
                .thumbnail(itemBundle.getThumbnail()==null ? null : CommonUtils.makeCloudFrontAdminUrl(itemBundle.getThumbnail()))
                .build();
    }

    public static ItemBundleDto of(Long id, String name, Long categoryId, Long brandId, String thumbnail, String description, Boolean isRecommend, String createDate){
        return ItemBundleDto.builder()
                .id(id)
                .name(name)
                .categoryId(categoryId)
                .brandId(brandId)
                .thumbnail(CommonUtils.makeCloudFrontAdminUrl(thumbnail))
                .isRecommend(isRecommend)
                .description(description)
                .createDate(createDate)
                .build();
    }

    public static List<ItemBundleDto> of(List<ItemBundle> list){
        return list.stream().map(p -> ItemBundleDto.of(p.getId(), p.getName(), p.getCategory().getId(), p.getBrand().getId(), p.getThumbnail(), p.getDescription(), p.getIsRecommend(), p.getCreateDate().toLocalDate().toString()))
                .collect(Collectors.toList());
    }
}
