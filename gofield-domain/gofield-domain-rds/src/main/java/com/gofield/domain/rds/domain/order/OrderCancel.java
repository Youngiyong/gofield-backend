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
    @JoinColumn(name = "shipping_template_id")
    private ShippingTemplate shippingTemplate;

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
    private OrderCancel(Order order, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, EOrderCancelTypeFlag type, EOrderCancelStatusFlag status, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                        int totalDelivery, int totalDiscount, int totalPg, String refundName, String refundAccount, String refundBank) {
        this.order = order;
        this.orderCancelComment = orderCancelComment;
        this.shippingTemplate = shippingTemplate;
        this.type = type;
        this.status = status;
        this.code = code;
        this.reason = reason;
        this.totalAmount = totalAmount;
        this.totalItem = totalItem;
        this.totalDelivery = totalDelivery;
        this.totalDiscount = totalDiscount;
        this.totalPg = totalPg;
        this.refundName = refundName;
        this.refundAccount = refundAccount;
        this.refundBank = refundBank;
    }


    public static OrderCancel newChangeInstance(Order order, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem){
        return OrderCancel.builder()
                .order(order)
                .orderCancelComment(orderCancelComment)
                .shippingTemplate(shippingTemplate)
                .status(EOrderCancelStatusFlag.ORDER_CHANGE_REQUEST)
                .type(EOrderCancelTypeFlag.CHANGE)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .build();
    }


    public static OrderCancel newCancelInstance(Order order, OrderCancelComment orderCancelComment, ShippingTemplate shippingTemplate, EOrderCancelTypeFlag cancelType,  EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                                          int totalDelivery, int totalDiscount, int totalPg,  String refundName, String refundAccount, String refundBank) {
        return OrderCancel.builder()
                .order(order)
                .orderCancelComment(orderCancelComment)
                .shippingTemplate(shippingTemplate)
                .type(cancelType)
                .status(EOrderCancelStatusFlag.ORDER_CANCEL_REQUEST)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalDiscount(totalDiscount)
                .totalPg(totalPg)
                .refundName(refundName)
                .refundAccount(refundAccount)
                .refundBank(refundBank)
                .build();
    }

    public void addOrderCancelItem(OrderCancelItem orderCancelItem){
        this.orderCancelItems.add(orderCancelItem);
    }

}
