package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.seller.ShippingTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShippingTemplateResponse {
    private Long id;
    private Long sellerId;
    private String title;
    private Boolean isCondition;
    private int condition;
    private EItemChargeFlag chargeType;
    private int charge;
    private Boolean isPaid;
    private String exchangeCourierName;
    private int exchangeCharge;
    private int takebackCharge;
    private Boolean isFee;
    private int feeJeju;
    private int feeJejuBesides;
    private String shippingComment;

    @Builder
    private ShippingTemplateResponse(Long id, Long sellerId, String title, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge,
                                     Boolean isPaid, String exchangeCourierName, int exchangeCharge, int takebackCharge, Boolean isFee, int feeJeju, int feeJejuBesides, String shippingComment){
        this.id = id;
        this.sellerId = sellerId;
        this.title = title;
        this.isCondition = isCondition;
        this.condition = condition;
        this.chargeType = chargeType;
        this.charge = charge;
        this.isPaid = isPaid;
        this.exchangeCourierName = exchangeCourierName;
        this.exchangeCharge = exchangeCharge;
        this.takebackCharge = takebackCharge;
        this.isFee = isFee;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
        this.shippingComment = shippingComment;
    }

    public static ShippingTemplateResponse of(ShippingTemplate shippingTemplate){
        return ShippingTemplateResponse.builder()
                .id(shippingTemplate.getId())
                .sellerId(shippingTemplate.getSeller().getId())
                .title(shippingTemplate.getTitle())
                .isCondition(shippingTemplate.getIsCondition())
                .condition(shippingTemplate.getCondition())
                .chargeType(shippingTemplate.getChargeType())
                .charge(shippingTemplate.getCharge())
                .isPaid(shippingTemplate.getIsPaid())
                .exchangeCourierName(shippingTemplate.getExchangeCourierName())
                .takebackCharge(shippingTemplate.getTakebackCharge())
                .isFee(shippingTemplate.getIsFee())
                .feeJeju(shippingTemplate.getFeeJeju())
                .feeJejuBesides(shippingTemplate.getFeeJejuBesides())
                .shippingComment(shippingTemplate.getShippingComment())
                .build();
    }

}
