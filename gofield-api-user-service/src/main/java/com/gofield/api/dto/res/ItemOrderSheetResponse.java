package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
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
    private EItemOptionTypeFlag optionType;
    private EItemChargeFlag chargeType;
    private int charge;
    private int condition;
    private int feeJeju;
    private int feeJejuBesides;


    @Builder
    private ItemOrderSheetResponse(Long id, Long sellerId, String brandName, String name, List<String> optionName, String thumbnail,
                                   String itemNumber, int price, int qty, int deliveryPrice, Long optionId, Boolean isOption, EItemOptionTypeFlag optionType, EItemChargeFlag chargeType, int charge, int condition, int feeJeju, int feeJejuBesides){
        this.id = id;
        this.sellerId = sellerId;
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
        this.optionType = optionType;
        this.chargeType = chargeType;
        this.charge = charge;
        this.condition = condition;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
    }

    /*
    ToDo: 배송비는 우션 0원처리
     */
    public static ItemOrderSheetResponse of(Long id, Long sellerId, String brandName, String name, String optionName, String thumbnail,
                                            String itemNumber, int price, int qty, int deliveryPrice, Long optionId, Boolean isOption, EItemOptionTypeFlag optionType,  EItemChargeFlag chargeType,  int charge, int condition, int feeJeju, int feeJejuBesides){
        try {
            return ItemOrderSheetResponse.builder()
                    .id(id)
                    .sellerId(sellerId)
                    .brandName(brandName)
                    .name(name)
                    .optionName(optionName==null ? null : new ObjectMapper().readValue(optionName, new TypeReference<List<String>>(){}))
                    .thumbnail(thumbnail)
                    .itemNumber(itemNumber)
                    .price(price)
                    .qty(qty)
                    .deliveryPrice(deliveryPrice)
                    .optionId(optionId)
                    .isOption(isOption)
                    .optionType(optionType)
                    .chargeType(chargeType)
                    .charge(charge)
                    .condition(condition)
                    .feeJeju(feeJeju)
                    .feeJejuBesides(feeJejuBesides)
                    .build();
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }


}
