package com.gofield.admin.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.admin.util.AdminUtil;
import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;
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
    private List<ItemBundleDto> bundleList;
    private Long bundleId;
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
    private EItemGenderFlag gender;
    private String length;
    private String weight;
    private String isAs;
    private EItemSpecFlag spec;
    private EItemPickupFlag pickup;
    private EItemStatusFlag status;
    private String thumbnail;
    private String createDate;
    private ShippingTemplateDto shippingTemplate;

    private Boolean isOption;
    private String optionInfo;

    @Builder
    private ItemDto(Long id, List<ItemBundleDto> bundleList, Long bundleId, Long detailId, Long shippingTemplateId, String name, String itemNumber,
                        int price, int qty, EItemClassificationFlag classification, EItemDeliveryFlag delivery, int deliveryPrice, List<String> tags,
                        String description, String manufacturer, String origin, EItemGenderFlag gender, String length,
                        String weight, String isAs, EItemSpecFlag spec, EItemPickupFlag pickup, EItemStatusFlag status, String thumbnail, String createDate, ShippingTemplateDto shippingTemplate,
                        String optionInfo){
        this.id = id;
        this.bundleList = bundleList;
        this.bundleId = bundleId;
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
        this.gender = gender;
        this.length = length;
        this.weight = weight;
        this.isAs = isAs;
        this.spec = spec;
        this.pickup = pickup;
        this.status = status;
        this.thumbnail = thumbnail;
        this.createDate = createDate;
        this.shippingTemplate = shippingTemplate;
        this.optionInfo = optionInfo;
    }

    public static ItemDto of(List<ItemBundleDto> bundleList){
        return ItemDto.builder()
                .bundleList(bundleList)
                .shippingTemplate(new ShippingTemplateDto())
                .build();
    }


    public static ItemDto of(List<ItemBundleDto> bundleList, Item item, ItemStock itemStock, List<ItemOptionProjection> optionList){
        ShippingTemplateDto shippingTemplateDto = ShippingTemplateDto.of(item.getShippingTemplate());
        List<ItemKeyValueDto> optionKeyValueList = item.getDetail().getItemOption()==null ? null : AdminUtil.strToObject(item.getDetail().getItemOption(), new TypeReference<List<ItemKeyValueDto>>(){});
        String origin = null;
        String manufacturer = null;
        String isAs = null;
        String length = null;
        String weight = null;
        if(optionKeyValueList!=null){
            for(ItemKeyValueDto map : optionKeyValueList){
                switch (map.getKey()){
                    case "제조사":
                        manufacturer = map.getValue();
                        break;
                    case "원산지":
                        origin = map.getValue();
                        break;
                    case "AS 가능여부" :
                        isAs = map.getValue();
                        break;
                    case "길이":
                        length = map.getValue();
                        break;
                    case "중량":
                        weight = map.getValue();
                        break;
                }
            }
        }

        ItemOptionGroupDto itemOptionGroupDto = null;
        List<ItemNameValueDto> itemNameValueDtoList = null;
        List<ItemOptionItemDto> itemOptionItemDtoList = null;
        ItemOptionManagerEditDto itemOptionManagerEditDto = null;
        if(optionList!=null){
            itemNameValueDtoList = ItemNameValueDto.ofList(item.getOptionGroups());
            itemOptionItemDtoList = ItemOptionItemDto.ofList(optionList);
            itemOptionManagerEditDto = ItemOptionManagerEditDto.of(item.getIsOption(), optionList.get(0).getOptionType(), itemNameValueDtoList, itemOptionItemDtoList);
        }

       return ItemDto.builder()
               .id(item.getId())
               .bundleList(bundleList)
               .bundleId(item.getBundle().getId())
               .detailId(item.getDetail().getId())
               .shippingTemplateId(item.getShippingTemplate().getId())
               .name(item.getName())
               .itemNumber(item.getItemNumber())
               .thumbnail(CommonUtils.makeCloudFrontAdminUrl(item.getThumbnail()))
               .status(itemStock.getStatus())
               .price(item.getPrice())
               .qty(itemStock.getQty())
               .classification(item.getClassification())
               .delivery(item.getDelivery())
               .deliveryPrice(item.getDeliveryPrice())
               .tags(item.getTags()==null ? null : AdminUtil.strToObject(item.getTags(), new TypeReference<List<String>>(){}))
               .description(item.getDetail().getDescription())
               .manufacturer(manufacturer)
               .origin(origin)
               .isAs(isAs)
               .length(length)
               .weight(weight)
               .gender(item.getDetail().getGender())
               .spec(item.getDetail().getSpec())
               .pickup(item.getPickup())
               .status(itemStock.getStatus())
               .createDate(itemStock.getCreateDate().toString())
               .shippingTemplate(shippingTemplateDto)
               .optionInfo(AdminUtil.toJsonStr(itemOptionManagerEditDto))
               .build();
    }
}
