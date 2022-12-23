package com.gofield.admin.dto;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShippingTemplateDto {
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

    private String zipCode;

    private String address;

    private String addressExtra;

    private String returnZipCode;

    private String receiver;

    private String receiverTel;

    private String returnAddress;

    private String returnAddressExtra;

    @Builder
    private ShippingTemplateDto(Long id, Long sellerId, String title, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge,
                                Boolean isPaid, String exchangeCourierName, int exchangeCharge, int takebackCharge, Boolean isFee, int feeJeju, int feeJejuBesides, String shippingComment,
                                String zipCode, String address, String addressExtra, String returnZipCode, String receiver, String receiverTel, String returnAddress, String returnAddressExtra){
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
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.returnZipCode = returnZipCode;
        this.receiver = receiver;
        this.receiverTel = receiverTel;
        this.returnAddress = returnAddress;
        this.returnAddressExtra = returnAddressExtra;
    }

    public static ShippingTemplateDto of(ShippingTemplate shippingTemplate){
        return ShippingTemplateDto.builder()
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
                .zipCode(shippingTemplate.getZipCode())
                .address(shippingTemplate.getAddress())
                .addressExtra(shippingTemplate.getAddressExtra())
                .returnZipCode(shippingTemplate.getReturnZipCode())
                .receiver(shippingTemplate.getReceiver())
                .receiverTel(shippingTemplate.getReceiverTel())
                .returnAddress(shippingTemplate.getReturnAddress())
                .returnAddressExtra(shippingTemplate.getReturnAddressExtra())
                .build();
    }

}
