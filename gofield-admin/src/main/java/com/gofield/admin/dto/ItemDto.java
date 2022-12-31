package com.gofield.admin.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.admin.util.AdminUtil;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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
    private int deliveryPrice;
    private List<String> tags;
    private String description;
    //제조사
    private String manufacturer;
    //원산지
    private String origin;

    private String itemSpec;

    private EItemGenderFlag gender;

    private String length;

    private String weight;

    private Boolean isAs;

    private EItemSpecFlag spec;

    private EItemPickupFlag pickup;

    private List<ItemDetailOptionDto> options;

    private String thumbnail;

    private String createDate;
    private ShippingTemplateDto shippingTemplate;
    private String optionInfo;

    @Builder
    private ItemDto(Long id, List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList, Long bundleId, Long categoryId,
                        String categoryName, Long brandId, String brandName, Long detailId, Long shippingTemplateId, String name, String itemNumber,
                        int price, int qty, EItemClassificationFlag classification, EItemDeliveryFlag delivery, int deliveryPrice, List<String> tags,
                        String description, String manufacturer, String origin, String itemSpec, EItemGenderFlag gender, String length,
                        String weight, Boolean isAs, EItemSpecFlag spec, EItemPickupFlag pickup, List<ItemDetailOptionDto> options, String thumbnail, String createDate, ShippingTemplateDto shippingTemplate,
                    String optionInfo){
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
        this.deliveryPrice = deliveryPrice;
        this.tags = tags;
        this.description = description;
        this.manufacturer = manufacturer;
        this.origin = origin;
        this.itemSpec = itemSpec;
        this.gender = gender;
        this.length = length;
        this.weight = weight;
        this.isAs = isAs;
        this.spec = spec;
        this.pickup = pickup;
        this.options = options;
        this.thumbnail = thumbnail;
        this.createDate = createDate;
        this.shippingTemplate = shippingTemplate;
        this.optionInfo = optionInfo;
    }

    public static ItemDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList){
        return ItemDto.builder()
                .categoryList(categoryList)
                .brandList(brandList)
                .bundleList(bundleList)
                .shippingTemplate(new ShippingTemplateDto())
                .build();
    }


    public static ItemDto of(List<CategoryDto> categoryList, List<BrandDto> brandList, List<ItemBundleDto> bundleList, Item item){
       return ItemDto.builder()
               .id(item.getId())
               .categoryList(categoryList)
               .brandList(brandList)
               .bundleList(bundleList)
               .bundleId(item.getBundle().getId())
               .categoryId(item.getCategory().getId())
               .categoryName(item.getCategory().getName())
               .brandId(item.getBrand().getId())
               .brandName(item.getBrand().getName())
               .detailId(item.getDetail().getId())
               .shippingTemplateId(item.getShippingTemplate().getId())
               .name(item.getName())
               .itemNumber(item.getItemNumber())
               //qty
               //price
               .price(item.getPrice())
               .qty(0)
               .classification(item.getClassification())
               .delivery(item.getDelivery())
               .deliveryPrice(item.getDeliveryPrice())
               .tags(item.getTags()==null ? null : AdminUtil.strToObject(item.getTags(), new TypeReference<List<String>>(){}))
               .description(item.getDetail().getDescription())
               .build();
    }
}
