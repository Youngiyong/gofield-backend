package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.EOrderItemStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCancelChangeItemResponse {
    private Long id;
    private Long orderId;
    private Long itemId;
    private Long itemOptionId;
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

    @Builder
    private OrderCancelChangeItemResponse(Long id, Long orderId, Long itemId, Long itemOptionId, String itemNumber,
                                          String name, List<String> optionName, String thumbnail, EOrderItemStatusFlag status, Boolean isOption,
                                          int qty, int totalAmount, int itemPrice, int discountPrice, int deliveryPrice, int refundPrice, String paymentCompany,
                                          String paymentType, String cardNumber, String cardType, int installmentPlanMonth, EOrderCancelReasonFlag reason,
                                          String refundName, String refundAccount, String refundBank){
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemOptionId = itemOptionId;
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
    }

    public static OrderCancelChangeItemResponse of(OrderItem orderItem, EOrderCancelReasonFlag reason, String refundName, String refundAccount, String refundBank){
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
            ToDO: ????????? ?????? ?????? ???????????? ??????
             */
        }
        int refundPrice = orderItem.getItem().getShippingTemplate()==null ? 0 : orderItem.getItem().getShippingTemplate().getTakebackCharge();
        int totalAmount = itemPrice * qty - discountPrice - deliveryPrice - refundPrice;

        return OrderCancelChangeItemResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .itemId(orderItem.getOrder().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null : ApiUtil.strToObject(orderItem.getOrderItemOption().getName(), new TypeReference<List<String>>(){}))
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
                .reason(reason)
                .refundBank(refundBank)
                .refundAccount(refundAccount)
                .refundName(refundName)
                .build();
    }

}
