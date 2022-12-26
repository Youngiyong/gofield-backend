package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.ShippingTemplate;
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
    private String receiver;
    private String receiverTel;
    private String zipCode;
    private String address;
    private String addressExtra;
    private String returnZipCode;
    private String returnAddress;
    private String returnAddressExtra;

    @Builder
    private ShippingTemplateResponse(Long id, Long sellerId, String title, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge,
                                     Boolean isPaid, String exchangeCourierName, int exchangeCharge, int takebackCharge, Boolean isFee, int feeJeju,
                                     int feeJejuBesides, String shippingComment, String receiver, String receiverTel, String zipCode, String address, String addressExtra, String returnZipCode, String returnAddress, String returnAddressExtra){
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
        this.receiver = receiver;
        this.receiverTel = receiverTel;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.returnZipCode = returnZipCode;
        this.returnAddress = returnAddress;
        this.returnAddressExtra = returnAddressExtra;
    }

    public static ShippingTemplateResponse of(ShippingTemplate shippingTemplate){
        return ShippingTemplateResponse.builder()
                .id(shippingTemplate.getId())
                .sellerId(shippingTemplate.getSellerId())
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
                .receiver(shippingTemplate.getReceiver())
                .receiverTel(shippingTemplate.getReceiverTel())
                .zipCode(shippingTemplate.getZipCode())
                .address(shippingTemplate.getAddress())
                .addressExtra(shippingTemplate.getAddressExtra())
                .returnZipCode(shippingTemplate.getReturnZipCode())
                .returnAddress(shippingTemplate.getReturnAddress())
                .returnAddressExtra(shippingTemplate.getReturnAddressExtra())
                .build();
    }

}
