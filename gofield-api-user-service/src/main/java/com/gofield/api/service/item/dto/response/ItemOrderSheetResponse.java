package com.gofield.api.service.item.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemOrderSheetResponse {

    private Long id;
    private Long sellerId;
    private Long bundleId;
    private Long categoryId;
    private Long brandId;
    private String brandName;
    private String name;
    private List<String> optionName;
    private String thumbnail;
    private String itemNumber;
    private int price;
    private int qty;
    private int deliveryPrice;
    private Long optionId;
    private Boolean isOption;
    private EItemDeliveryFlag delivery;
    private EItemOptionTypeFlag optionType;
    private EItemChargeFlag chargeType;
    private Boolean isPaid;
    private int charge;
    private int condition;
    private int feeJeju;
    private int feeJejuBesides;


    @Builder
    private ItemOrderSheetResponse(Long id, Long sellerId, Long bundleId, Long categoryId, Long brandId, String brandName, String name, List<String> optionName, String thumbnail,
                                   String itemNumber, int price, int qty, int deliveryPrice, Long optionId, Boolean isOption, EItemDeliveryFlag delivery, EItemOptionTypeFlag optionType, EItemChargeFlag chargeType, Boolean isPaid, int charge, int condition, int feeJeju, int feeJejuBesides){
        this.id = id;
        this.sellerId = sellerId;
        this.bundleId = bundleId;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.name = name;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.deliveryPrice = deliveryPrice;
        this.optionId = optionId;
        this.isOption = isOption;
        this.delivery = delivery;
        this.optionType = optionType;
        this.chargeType = chargeType;
        this.isPaid = isPaid;
        this.charge = charge;
        this.condition = condition;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
    }


    public static ItemOrderSheetResponse of(Long id, Long sellerId, Long bundleId, Long categoryId, Long brandId,  String brandName, String name, String optionName, String thumbnail,
                                            String itemNumber, int price, int qty, int deliveryPrice, Long optionId, Boolean isOption, EItemDeliveryFlag delivery, EItemOptionTypeFlag optionType,  EItemChargeFlag chargeType, Boolean isPaid,  int charge, int condition, int feeJeju, int feeJejuBesides){
        return ItemOrderSheetResponse.builder()
                .id(id)
                .sellerId(sellerId)
                .bundleId(bundleId)
                .categoryId(categoryId)
                .brandId(brandId)
                .brandName(brandName)
                .name(name)
                .optionName(optionName==null ? null : ObjectMapperUtils.strToObject(optionName, new TypeReference<List<String>>(){}))
                .thumbnail(CommonUtils.makeCloudFrontUrl(thumbnail))
                .itemNumber(itemNumber)
                .price(price)
                .qty(qty)
                .deliveryPrice(deliveryPrice)
                .optionId(optionId)
                .isOption(isOption)
                .delivery(delivery)
                .isPaid(isPaid)
                .optionType(optionType)
                .chargeType(chargeType)
                .charge(charge)
                .condition(condition)
                .feeJeju(feeJeju)
                .feeJejuBesides(feeJejuBesides)
                .build();
    }


}
