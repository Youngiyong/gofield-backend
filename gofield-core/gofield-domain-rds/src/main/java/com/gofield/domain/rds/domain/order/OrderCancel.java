package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.ShippingTemplate;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_cancel")
public class OrderCancel extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping orderShipping;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_template_id")
    private ShippingTemplate shippingTemplate;

    @Column(nullable = false, length = 16)
    private String cancelNumber;

    @Column(name = "type_flag", nullable = false)
    private EOrderCancelTypeFlag type;

    @Column(name = "status_flag", nullable = false)
    private EOrderCancelStatusFlag status;

    @Column(name = "code_flag", nullable = false)
    private EOrderCancelCodeFlag code;

    @Column(name = "reason_flag", nullable = false)
    private EOrderCancelReasonFlag reason;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private int totalItem;

    @Column(nullable = false)
    private int totalDelivery;

    @Column(nullable = false)
    private int totalDiscount;

    @Column(nullable = false)
    private int totalPg;

    @Column(nullable = false)
    private int totalSafeCharge;

    @Column(nullable = false)
    private int totalRefund;

    @Column
    private String carrier;

    @Column
    private String trackingNumber;

    @Column
    private String refundName;

    @Column
    private String refundAccount;

    @Column
    private String refundBank;

    @Column
    private LocalDateTime recalledDate;

    @Column
    private LocalDateTime refundDate;

    @OneToMany(mappedBy = "cancel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderCancelItem> orderCancelItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private OrderCancelComment orderCancelComment;

    @Builder
    private OrderCancel(Order order, OrderShipping orderShipping, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, String cancelNumber, EOrderCancelTypeFlag type, EOrderCancelStatusFlag status, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                        int totalDelivery, int totalDiscount, int totalSafeCharge, int totalRefund, int totalPg, String refundName, String refundAccount, String refundBank) {
        this.order = order;
        this.orderShipping = orderShipping;
        this.orderCancelComment = orderCancelComment;
        this.shippingTemplate = shippingTemplate;
        this.cancelNumber = cancelNumber;
        this.type = type;
        this.status = status;
        this.code = code;
        this.reason = reason;
        this.totalAmount = totalAmount;
        this.totalItem = totalItem;
        this.totalDelivery = totalDelivery;
        this.totalDiscount = totalDiscount;
        this.totalSafeCharge = totalSafeCharge;
        this.totalRefund = totalRefund;
        this.totalPg = totalPg;
        this.refundName = refundName;
        this.refundAccount = refundAccount;
        this.refundBank = refundBank;
    }


    public static OrderCancel newChangeInstance(Order order, OrderShipping orderShipping, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, String cancelNumber,  EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem, int totalDelivery, int totalRefund){
        return OrderCancel.builder()
                .order(order)
                .orderShipping(orderShipping)
                .orderCancelComment(orderCancelComment)
                .shippingTemplate(shippingTemplate)
                .cancelNumber(cancelNumber)
                .status(EOrderCancelStatusFlag.ORDER_CHANGE_REQUEST)
                .type(EOrderCancelTypeFlag.CHANGE)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalRefund(totalRefund)
                .build();
    }

    public static OrderCancel newReturnInstance(Order order, OrderShipping orderShipping, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, String cancelNumber, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                                                int totalDelivery, int totalDiscount, int totalRefund, int totalPg,  String refundName, String refundAccount, String refundBank) {
        return OrderCancel.builder()
                .order(order)
                .orderShipping(orderShipping)
                .orderCancelComment(orderCancelComment)
                .shippingTemplate(shippingTemplate)
                .cancelNumber(cancelNumber)
                .type(EOrderCancelTypeFlag.RETURN)
                .status(EOrderCancelStatusFlag.ORDER_RETURN_REQUEST)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalDiscount(totalDiscount)
                .totalRefund(totalRefund)
                .totalPg(totalPg)
                .refundName(refundName)
                .refundAccount(refundAccount)
                .refundBank(refundBank)
                .build();
    }


    public static OrderCancel newCancelInstance(Order order, OrderShipping orderShipping, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, String cancelNumber, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                                          int totalDelivery, int totalDiscount, int totalRefund, int totalSafeCharge, int totalPg,  String refundName, String refundAccount, String refundBank) {
        return OrderCancel.builder()
                .order(order)
                .orderShipping(orderShipping)
                .orderCancelComment(orderCancelComment)
                .shippingTemplate(shippingTemplate)
                .cancelNumber(cancelNumber)
                .type(EOrderCancelTypeFlag.CANCEL)
                .status(EOrderCancelStatusFlag.ORDER_CANCEL_REQUEST)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalDiscount(totalDiscount)
                .totalRefund(totalRefund)
                .totalSafeCharge(totalSafeCharge)
                .totalPg(totalPg)
                .refundName(refundName)
                .refundAccount(refundAccount)
                .refundBank(refundBank)
                .build();
    }

    public static OrderCancel newCancelCompleteInstance(Order order, OrderShipping orderShipping,  OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, String cancelNumber, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                                                        int totalDelivery, int totalDiscount, int totalRefund, int totalPg,  String refundName, String refundAccount, String refundBank) {

        return OrderCancel.builder()
                .order(order)
                .orderShipping(orderShipping)
                .orderCancelComment(orderCancelComment)
                .shippingTemplate(shippingTemplate)
                .cancelNumber(cancelNumber)
                .status(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)
                .type(EOrderCancelTypeFlag.CANCEL)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalDiscount(totalDiscount)
                .totalRefund(totalRefund)
                .totalPg(totalPg)
                .refundName(refundName)
                .refundAccount(refundAccount)
                .refundBank(refundBank)
                .build();
    }

    public void addOrderCancelItem(OrderCancelItem orderCancelItem){
        this.orderCancelItems.add(orderCancelItem);
    }

    public void updateAdminCancelStatus(EOrderCancelStatusFlag status){
        if(status.equals(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)){
            this.status = EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_CANCEL_DENIED)){
            this.status = EOrderCancelStatusFlag.ORDER_CANCEL_DENIED;
        }
    }

    public void updateAdminReturnStatus(EOrderCancelStatusFlag status){
        if(status.equals(EOrderCancelStatusFlag.ORDER_RETURN_COLLECT_PROCESS)){
            this.status = EOrderCancelStatusFlag.ORDER_RETURN_COLLECT_PROCESS;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_RETURN_COLLECT_PROCESS_COMPLETE)){
            this.status = EOrderCancelStatusFlag.ORDER_RETURN_COLLECT_PROCESS_COMPLETE;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_RETURN_COMPLETE)){
            this.status = EOrderCancelStatusFlag.ORDER_RETURN_COMPLETE;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_RETURN_DENIED)){
            this.status = EOrderCancelStatusFlag.ORDER_RETURN_DENIED;
        }
    }

    public void updateAdminChangeStatus(EOrderCancelStatusFlag status){
        if(status.equals(EOrderCancelStatusFlag.ORDER_CHANGE_COLLECT_PROCESS)){
            this.status = EOrderCancelStatusFlag.ORDER_CHANGE_COLLECT_PROCESS;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_CHANGE_COLLECT_PROCESS_COMPLETE)){
            this.status = EOrderCancelStatusFlag.ORDER_CHANGE_COLLECT_PROCESS_COMPLETE;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_CHANGE_REDELIVERY)){
            this.status = EOrderCancelStatusFlag.ORDER_CHANGE_REDELIVERY;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_CHANGE_DENIED)){
            this.status = EOrderCancelStatusFlag.ORDER_CHANGE_DENIED;
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_CHANGE_COMPLETE)) {
            this.status = EOrderCancelStatusFlag.ORDER_CHANGE_COMPLETE;
        }
    }
}
