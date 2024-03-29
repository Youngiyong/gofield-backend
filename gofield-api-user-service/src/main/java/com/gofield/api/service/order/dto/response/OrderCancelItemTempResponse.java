package com.gofield.api.service.order.dto.response;

import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.order.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCancelItemTempResponse {
    private Long id;
    private Long orderId;
    private Long itemId;
    private Long itemOptionId;
    private Long shippingTemplateId;
    private String itemNumber;
    private String name;
    private String optionName;
    private String thumbnail;
    private EOrderItemStatusFlag status;
    private Boolean isOption;

    private Boolean isPaid;
    private int qty;
    private int totalAmount;
    private int itemPrice;
    private int discountPrice;
    private int deliveryPrice;
    private int safeChargePrice;
    private int refundPrice;
    private String paymentCompany;
    private String paymentType;
    private String cardNumber;
    private String cardType;
    private int installmentPlanMonth;
    private String refundName;
    private String refundAccount;
    private String refundBank;
    private String userTel;
    private String username;
    private String zipCode;
    private String address;
    private String addressExtra;



    @Builder
    private OrderCancelItemTempResponse(Long id, Long orderId, Long itemId, Long itemOptionId, Long shippingTemplateId, String itemNumber,
                                        String name, String optionName, String thumbnail, EOrderItemStatusFlag status, Boolean isOption, Boolean isPaid,
                                        int qty, int totalAmount, int itemPrice, int discountPrice, int deliveryPrice, int safeChargePrice, int refundPrice, String paymentCompany,
                                        String paymentType, String cardNumber, String cardType, int installmentPlanMonth,
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
        this.isPaid = isPaid;
        this.qty = qty;
        this.totalAmount = totalAmount;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
        this.deliveryPrice = deliveryPrice;
        this.safeChargePrice = safeChargePrice;
        this.refundPrice = refundPrice;
        this.paymentCompany = paymentCompany;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.installmentPlanMonth = installmentPlanMonth;
        this.refundName = refundName;
        this.refundAccount  = refundAccount;
        this.refundBank = refundBank;
        this.userTel = userTel;
        this.username = username;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
    }


    public static OrderCancelItemTempResponse ofReturn(OrderItem orderItem, String refundName, String refundAccount, String refundBank){
        int qty = orderItem.getOrderItemOption()==null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty();
        int itemPrice = orderItem.getOrderItemOption()==null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice();
        int refundPrice = 0;
        int discountPrice = 0;
        int deliveryPrice = 0;
        if(orderItem.getItem().getDelivery().equals(EItemDeliveryFlag.PAY)){
            deliveryPrice = orderItem.getOrderShipping().getDeliveryPrice();
        } else if(orderItem.getItem().getDelivery().equals(EItemDeliveryFlag.FREE)){
            /*
            ToDO: 조건부 배송 추후 정해지면 처리
             */
            if(orderItem.getQty()*orderItem.getPrice()<orderItem.getItem().getShippingTemplate().getCondition()){
                deliveryPrice = orderItem.getItem().getShippingTemplate().getCharge();
            } else {
                deliveryPrice = 0;
            }
        }
        refundPrice = orderItem.getItem().getShippingTemplate().getTakebackCharge();
        int totalAmount = itemPrice * qty + deliveryPrice - discountPrice - refundPrice;
        OrderShippingAddress orderShippingAddress = orderItem.getOrder().getShippingAddress();
        return OrderCancelItemTempResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .itemId(orderItem.getItem().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .shippingTemplateId(orderItem.getItem().getShippingTemplate()==null ? null : orderItem.getItem().getShippingTemplate().getId())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null :orderItem.getOrderItemOption().getName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(orderItem.getItem().getThumbnail()))
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

    public static OrderCancelItemTempResponse of(OrderItem orderItem, String refundName, String refundAccount, String refundBank){
        int qty = orderItem.getOrderItemOption()==null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty();
        int itemPrice = orderItem.getOrderItemOption()==null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice();
        int discountPrice = 0;
        int deliveryPrice = 0;
        Double safeChargePrice = 0.0;
        Item item = orderItem.getItem();
        if(orderItem.getItem().getDelivery().equals(EItemDeliveryFlag.PAY)){
            if(!item.getShippingTemplate().getIsPaid()){
                deliveryPrice = orderItem.getItem().getDeliveryPrice();
            }
        }
        safeChargePrice = itemPrice * qty  * Constants.SAFE_PAYMENT_CHARGE / 100.0;
        int totalAmount = itemPrice * qty + deliveryPrice +  safeChargePrice.intValue() - discountPrice;
        OrderShippingAddress orderShippingAddress = orderItem.getOrder().getShippingAddress();
        return OrderCancelItemTempResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .itemId(orderItem.getItem().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .shippingTemplateId(orderItem.getItem().getShippingTemplate()==null ? null : orderItem.getItem().getShippingTemplate().getId())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null :orderItem.getOrderItemOption().getName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(orderItem.getItem().getThumbnail()))
                .status(orderItem.getStatus())
                .isOption(orderItem.getOrderItemOption()==null ? false : true)
                .qty(qty)
                .totalAmount(totalAmount)
                .safeChargePrice(safeChargePrice.intValue())
                .itemPrice(itemPrice)
                .isPaid(item.getShippingTemplate().getIsPaid())
                .discountPrice(discountPrice)
                .deliveryPrice(deliveryPrice)
                .discountPrice(discountPrice)
                .paymentCompany(orderItem.getOrder().getPaymentCompany())
                .paymentType(orderItem.getOrder().getPaymentType())
                .cardNumber(orderItem.getOrder().getCardNumber())
                .cardType(orderItem.getOrder().getCardType())
                .installmentPlanMonth(orderItem.getOrder().getInstallmentPlanMonth())
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
