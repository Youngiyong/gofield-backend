package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;
import com.gofield.domain.rds.domain.item.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private String itemName;
    private String itemNumber;
    private Long sellerId;
    private String sellerName;
    private List<String> optionName;
    private String thumbnail;
    private int price;

    private int deliveryPrice;
    private int qty;
    private Boolean isOrder;
    private EItemClassificationFlag classification;
    private EItemDeliveryFlag delivery;
    private EItemSpecFlag spec;
    private EItemGenderFlag gender;
    private Boolean isCondition;
    private int condition;
    private EItemChargeFlag chargeType;
    private int charge;
    private int feeJeju;
    private int feeJejuBesides;

    @Builder
    private CartResponse(Long id, String itemName, String itemNumber, Long sellerId, String sellerName,
                         List<String> optionName, String thumbnail, int price, int deliveryPrice,  int qty, Boolean isOrder, EItemClassificationFlag classification, EItemDeliveryFlag delivery,
                         EItemSpecFlag spec, EItemGenderFlag gender, Boolean isCondition, int condition,
                         EItemChargeFlag chargeType, int charge, int feeJeju, int feeJejuBesides){
        this.id = id;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.qty = qty;
        this.isOrder = isOrder;
        this.delivery = delivery;
        this.classification = classification;
        this.spec = spec;
        this.gender = gender;
        this.isCondition = isCondition;
        this.condition = condition;
        this.chargeType = chargeType;
        this.charge = charge;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
    }

    public static CartResponse of(Long id, String itemName, String itemNumber, Long sellerId, String sellerName,
                                  List<String> optionName, String thumbnail, int price, int deliveryPrice,  int qty, Boolean isOrder, EItemClassificationFlag classification, EItemDeliveryFlag delivery,
                                  EItemSpecFlag spec, EItemGenderFlag gender, Boolean isCondition, int condition,
                                  EItemChargeFlag chargeType, int charge, int feeJeju, int feeJejuBesides){
        return CartResponse.builder()
                .id(id)
                .itemName(itemName)
                .itemNumber(itemNumber)
                .sellerId(sellerId)
                .sellerName(sellerName)
                .optionName(optionName)
                .thumbnail(thumbnail)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .qty(qty)
                .isOrder(isOrder)
                .classification(classification)
                .delivery(delivery)
                .spec(spec)
                .gender(gender)
                .isCondition(isCondition)
                .condition(condition)
                .chargeType(chargeType)
                .charge(charge)
                .feeJeju(feeJeju)
                .feeJejuBesides(feeJejuBesides)
                .build();
    }

    public static List<CartResponse> of(List<CartProjection> list){
        return list
                .stream()
                .map(p -> CartResponse.of(p.getId(), p.getItemName(), p.getItemNumber(), p.getSellerId(),
                            p.getSellerName(), p.getOptionName()==null ? null : ApiUtil.strToObject(p.getOptionName(), new TypeReference<List<String>>(){}),
                            p.getThumbnail(), p.getPrice(), p.getDeliveryPrice(), p.getQty(), p.getIsOrder(), p.getClassification(), p.getDelivery(), p.getSpec(),
                            p.getGender(), p.getIsCondition(), p.getCondition(), p.getChargeType(), p.getCharge(), p.getFeeJeju(), p.getFeeJejuBesides()))
                .collect(Collectors.toList());
    }
}
