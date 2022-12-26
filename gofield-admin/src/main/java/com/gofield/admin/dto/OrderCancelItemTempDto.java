package com.gofield.admin.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.admin.util.AdminUtil;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.order.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCancelItemTempDto {
    private Long id;
    private Long orderId;
    private Long itemId;
    private Long itemOptionId;
    private Long shippingTemplateId;
    private String itemNumber;
    private String name;
    private List<String> optionName;
    private String thumbnail;
    private EOrderItemStatusFlag status;
    private Boolean isOption;
    private int qty;
    private int totalAmount;
    private int itemPrice;
    private int discountPrice;
    private int deliveryPrice;
    private int refundPrice;
    private String paymentCompany;
    private String paymentType;
    private String cardNumber;
    private String cardType;
    private int installmentPlanMonth;
    private EOrderCancelReasonFlag reason;
    private String refundName;
    private String refundAccount;
    private String refundBank;
    private String userTel;
    private String username;
    private String zipCode;
    private String address;
    private String addressExtra;

    @Builder
    private OrderCancelItemTempDto(Long id, Long orderId, Long itemId, Long itemOptionId, Long shippingTemplateId, String itemNumber,
                                   String name, List<String> optionName, String thumbnail, EOrderItemStatusFlag status, Boolean isOption,
                                   int qty, int totalAmount, int itemPrice, int discountPrice, int deliveryPrice, int refundPrice, String paymentCompany,
                                   String paymentType, String cardNumber, String cardType, int installmentPlanMonth, EOrderCancelReasonFlag reason,
                                   String refundName, String refundAccount, String refundBank, String userTel, String username, String zipCode, String address, String addressExtra){
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemOptionId = itemOptionId;
        this.shippingTemplateId = shippingTemplateId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.status = status;
        this.isOption = isOption;
        this.qty = qty;
        this.totalAmount = totalAmount;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
        this.deliveryPrice = deliveryPrice;
        this.refundPrice = refundPrice;
        this.paymentCompany = paymentCompany;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.installmentPlanMonth = installmentPlanMonth;
        this.reason = reason;
        this.refundName = refundName;
        this.refundAccount  = refundAccount;
        this.refundBank = refundBank;
        this.userTel = userTel;
        this.username = username;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
    }

    public static OrderCancelItemTempDto of(OrderItem orderItem, EOrderCancelReasonFlag reason, String refundName, String refundAccount, String refundBank){
        int qty = orderItem.getOrderItemOption()==null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty();
        int itemPrice = orderItem.getOrderItemOption()==null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice();
        /*
           ToDo : DiscountPrice
        */
        int discountPrice = 0;
        int deliveryPrice = 0;
        if(orderItem.getItem().getDelivery().equals(EItemDeliveryFlag.PAY)){
            deliveryPrice = orderItem.getItem().getDeliveryPrice();
        } else if(orderItem.getItem().getDelivery().equals(EItemDeliveryFlag.CONDITION)){
            /*
            ToDO: 조건부 배송 추후 정해지면 처리
             */
        }
        int refundPrice = 0;

        OrderShipping orderShipping = orderItem.getOrderShipping();
        switch (orderShipping.getStatus()){
            case ORDER_SHIPPING_DELIVERY_COMPLETE: case ORDER_SHIPPING_COMPLETE:
                refundPrice = orderItem.getItem().getShippingTemplate()==null ? 0 : orderItem.getItem().getShippingTemplate().getTakebackCharge();
            default:
                refundPrice = 0;
        }

        int totalAmount = itemPrice * qty + deliveryPrice - discountPrice - refundPrice;
        OrderShippingAddress orderShippingAddress = orderItem.getOrder().getShippingAddress();
        return OrderCancelItemTempDto.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .itemId(orderItem.getItem().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .shippingTemplateId(orderItem.getItem().getShippingTemplate()==null ? null : orderItem.getItem().getShippingTemplate().getId())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null : AdminUtil.strToObject(orderItem.getOrderItemOption().getName(), new TypeReference<List<String>>(){}))
                .thumbnail(Constants.CDN_URL.concat(orderItem.getItem().getThumbnail().concat(Constants.RESIZE_200x200)))
                .status(orderItem.getStatus())
                .isOption(orderItem.getOrderItemOption()==null ? false : true)
                .qty(qty)
                .totalAmount(totalAmount)
                .itemPrice(itemPrice)
                .discountPrice(discountPrice)
                .deliveryPrice(deliveryPrice)
                .discountPrice(discountPrice)
                .refundPrice(refundPrice)
                .paymentCompany(orderItem.getOrder().getPaymentCompany())
                .paymentType(orderItem.getOrder().getPaymentType())
                .cardNumber(orderItem.getOrder().getCardNumber())
                .cardType(orderItem.getOrder().getCardType())
                .installmentPlanMonth(orderItem.getOrder().getInstallmentPlanMonth())
                .reason(reason)
                .refundBank(refundBank)
                .refundAccount(refundAccount)
                .refundName(refundName)
                .userTel(orderShippingAddress.getTel())
                .username(orderShippingAddress.getName())
                .zipCode(orderShippingAddress.getZipCode())
                .address(orderShippingAddress.getAddress())
                .addressExtra(orderShippingAddress.getAddressExtra())
                .build();
    }

}
