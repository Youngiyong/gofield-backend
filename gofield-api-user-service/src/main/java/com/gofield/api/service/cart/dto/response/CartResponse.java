package com.gofield.api.service.cart.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.CommonUtils;
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

    private Boolean isPaid;
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
                         List<String> optionName, String thumbnail, int price, int deliveryPrice,  int qty, Boolean isOrder, Boolean isPaid,  EItemClassificationFlag classification, EItemDeliveryFlag delivery,
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
        this.isPaid = isPaid;
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

    public static CartResponse of(CartProjection cartProjection){
        List<String> optionName = cartProjection.getOptionName()==null ? null : ObjectMapperUtils.strToObject(cartProjection.getOptionName(), new TypeReference<List<String>>(){});
        int deliveryPrice = 0;
        if(cartProjection.getDelivery().equals(EItemDeliveryFlag.PAY) && !cartProjection.getIsPaid()){
            deliveryPrice = cartProjection.getDeliveryPrice();
        }
        return CartResponse.builder()
                .id(cartProjection.getId())
                .itemName(cartProjection.getItemName())
                .itemNumber(cartProjection.getItemNumber())
                .sellerId(cartProjection.getSellerId())
                .sellerName(cartProjection.getSellerName())
                .optionName(optionName)
                .thumbnail(CommonUtils.makeCloudFrontUrl(cartProjection.getThumbnail()))
                .price(cartProjection.getPrice())
                .deliveryPrice(deliveryPrice)
                .qty(cartProjection.getQty())
                .isOrder(cartProjection.getIsOrder())
                .classification(cartProjection.getClassification())
                .delivery(cartProjection.getDelivery())
                .spec(cartProjection.getSpec())
                .gender(cartProjection.getGender())
                .isCondition(cartProjection.getIsCondition())
                .condition(cartProjection.getCondition())
                .isPaid(cartProjection.getIsPaid())
                .chargeType(cartProjection.getChargeType())
                .charge(cartProjection.getCharge())
                .feeJeju(cartProjection.getFeeJeju())
                .feeJejuBesides(cartProjection.getFeeJejuBesides())
                .build();
    }

    public static List<CartResponse> of(List<CartProjection> list){
        return list
                .stream()
                .map(CartResponse::of)
                .collect(Collectors.toList());
    }
}
