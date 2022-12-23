package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemUsedDto {
    private Long id;
    private List<CategoryDto> categoryList;

    private List<BrandDto> brandList;
    private List<ItemBundleDto> bundleList;
    private Long bundleId;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private Long detailId;
    private Long shippingTemplateId;
    private String name;
    private String itemNumber;
    private int price;
    private int qty;
    private EItemClassificationFlag classification;
    private EItemDeliveryFlag delivery;
    private EItemChargeFlag charge;
    private int deliveryPrice;
    private List<String> tags;
    private String description;

    private String summernote;
    //제조사
    private String manufacturer;
    //원산지
    private String origin;

    private String itemSpec;

    private EItemGenderFlag gender;

    private String length;

    private String weight;

    private Boolean isAs;

    private List<ItemDetailOptionDto> options;

    private String thumbnail;

    private String createDate;

    @Builder
    private ItemUsedDto(Long id, List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList, Long bundleId, Long categoryId,
                        String categoryName, Long brandId, String brandName, Long detailId, Long shippingTemplateId, String name, String itemNumber,
                        int price, int qty, EItemClassificationFlag classification, EItemDeliveryFlag delivery, EItemChargeFlag charge, int deliveryPrice, List<String> tags,
                        String description, String summernote, String manufacturer, String origin, String itemSpec, EItemGenderFlag gender, String length,
                        String weight, Boolean isAs, List<ItemDetailOptionDto> options, String thumbnail, String createDate){
        this.id = id;
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.bundleList = bundleList;
        this.bundleId = bundleId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.detailId = detailId;
        this.shippingTemplateId = shippingTemplateId;
        this.name = name;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.classification = classification;
        this.delivery = delivery;
        this.charge = charge;
        this.deliveryPrice = deliveryPrice;
        this.tags = tags;
        this.description = description;
        this.summernote = summernote;
        this.manufacturer = manufacturer;
        this.origin = origin;
        this.itemSpec = itemSpec;
        this.gender = gender;
        this.length = length;
        this.weight = weight;
        this.isAs = isAs;
        this.options = options;
        this.thumbnail = thumbnail;
        this.createDate = createDate;
    }

    public static ItemUsedDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList){
        return ItemUsedDto.builder()
                .categoryList(categoryList)
                .brandList(brandList)
                .bundleList(bundleList)
                .build();
    }

    public static ItemUsedDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList, ItemBundle itemBundle){
        return ItemUsedDto.builder()
                .id(itemBundle.getId())
                .name(itemBundle.getName())
                .categoryList(categoryList)
                .brandList(brandList)
                .bundleList(bundleList)
                .bundleId(itemBundle.getId())
                .categoryId(itemBundle.getCategory().getId())
                .categoryName(itemBundle.getCategory().getName())
                .qty(0)
                .price(0)
                .brandId(itemBundle.getBrand().getId())
                .brandName(itemBundle.getBrand().getName())
                .thumbnail(itemBundle.getThumbnail()==null ? null : Constants.CDN_URL.concat(itemBundle.getThumbnail()).concat(Constants.RESIZE_150x150))
                .build();
    }
}
