package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.EOrderItemStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCancelItemTempResponse {
    private Long id;

    private Long orderId;
    private Long itemId;
    private Long itemOptionId;
    private String itemNumber;
    private String name;
    private List<String> optionName;
    private String thumbnail;
    private EOrderItemStatusFlag status;

    private int qty;

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

    @Builder
    private OrderCancelItemTempResponse(Long id, Long orderId, Long itemId, Long itemOptionId, String itemNumber,
                                        String name, List<String> optionName, String thumbnail, EOrderItemStatusFlag status,
                                        int qty, int itemPrice, int discountPrice, int deliveryPrice, int refundPrice, String paymentCompany,
                                        String paymentType, String cardNumber, String cardType, int installmentPlanMonth, EOrderCancelReasonFlag reason){
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemOptionId = itemOptionId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.status = status;
        this.qty = qty;
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
    }

    public static OrderCancelItemTempResponse of(OrderItem orderItem, EOrderCancelReasonFlag reason){
        return OrderCancelItemTempResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .itemId(orderItem.getOrder().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null : ApiUtil.strToObject(orderItem.getOrderItemOption().getName(), new TypeReference<List<String>>(){}))
                .thumbnail(Constants.CDN_URL.concat(orderItem.getItem().getThumbnail().concat(Constants.RESIZE_150x150)))
                .status(orderItem.getStatus())
                .qty(orderItem.getOrderItemOption()==null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty())
                .itemPrice(orderItem.getOrderItemOption()==null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice())
                /*
                ToDo : DiscountPrice
                 */
                .discountPrice(0)
                .refundPrice(orderItem.getItem().getShippingTemplate()==null ? 0 : orderItem.getItem().getShippingTemplate().getTakebackCharge())
                .paymentCompany(orderItem.getOrder().getPaymentCompany())
                .paymentType(orderItem.getOrder().getPaymentType())
                .cardNumber(orderItem.getOrder().getCardNumber())
                .cardType(orderItem.getOrder().getCardType())
                .installmentPlanMonth(orderItem.getOrder().getInstallmentPlanMonth())
                .reason(reason)
                .build();
    }

}
