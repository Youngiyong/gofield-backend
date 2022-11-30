package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
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
    private int qty;
    private Boolean isOrder;
    private EItemClassificationFlag classification;
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
                         List<String> optionName, String thumbnail, int price, int qty, Boolean isOrder, EItemClassificationFlag classification,
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
        this.qty = qty;
        this.isOrder = isOrder;
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
                                  List<String> optionName, String thumbnail, int price, int qty, Boolean isOrder, EItemClassificationFlag classification,
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
                .qty(qty)
                .isOrder(isOrder)
                .classification(classification)
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
                .map(p -> {
                    try {
                        return CartResponse.of(p.getId(), p.getItemName(), p.getItemNumber(), p.getSellerId(),
                                p.getSellerName(), p.getOptionName()==null ? null : new ObjectMapper().readValue(p.getOptionName(), new TypeReference<List<String>>(){}),
                                p.getThumbnail(), p.getPrice(), p.getQty(), p.getIsOrder(), p.getClassification(), p.getSpec(),
                                p.getGender(), p.getIsCondition(), p.getCondition(), p.getChargeType(), p.getCharge(), p.getFeeJeju(), p.getFeeJejuBesides());
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }
}
