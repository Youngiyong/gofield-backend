package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
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
    private String thumbnail;
    private String createDate;

    @Builder
    private ItemDto(Long id, List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList, Long bundleId, Long categoryId,
                    String categoryName, Long brandId, String brandName, Long detailId, Long shippingTemplateId, String name, String itemNumber,
                    int price, int qty, EItemClassificationFlag classification, EItemDeliveryFlag delivery, EItemChargeFlag charge, int deliveryPrice, List<String> tags,
                    String description, String thumbnail, String createDate){
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
        this.thumbnail = thumbnail;
        this.createDate = createDate;
    }

    public static ItemDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList){
        return ItemDto.builder()
                .categoryList(categoryList)
                .brandList(brandList)
                .bundleList(bundleList)
                .build();
    }

    public static ItemDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList, ItemBundle itemBundle){
        return ItemDto.builder()
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