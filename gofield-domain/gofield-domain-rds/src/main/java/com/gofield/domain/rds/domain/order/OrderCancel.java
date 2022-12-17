package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false)
    private EOrderCancelTypeFlag type;

    @Column(name = "code_flag", nullable = false)
    private EOrderCancelFlagCodeModel code;

    @Column(name = "reason_flag")
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
    @Enumerated
    private EPaymentType paymentType;

    @Column
    private String carrier;

    @Column
    private String trackingNumber;

    @Column
    private String refundName;

    @Column
    private String refundCode;

    @Column
    private String refundBank;

    @Column
    private LocalDateTime recalledDate;

    @OneToMany(mappedBy = "cancel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderCancelItem> orderCancelItems = new ArrayList<>();

    @OneToMany(mappedBy = "cancel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderCancelComment> orderCancelComments = new ArrayList<>();

    @Builder
    private OrderCancel(Order order, EOrderCancelTypeFlag type, EOrderCancelFlagCodeModel code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                        int totalDelivery, int totalDiscount, int totalPg, EPaymentType paymentType, String refundName, String refundCode, String refundBank) {
        this.order = order;
        this.type = type;
        this.code = code;
        this.reason = reason;
        this.totalAmount = totalAmount;
        this.totalItem = totalItem;
        this.totalDelivery = totalDelivery;
        this.totalDiscount = totalDiscount;
        this.totalPg = totalPg;
        this.paymentType = paymentType;
        this.refundName = refundName;
        this.refundCode = refundCode;
        this.refundBank = refundBank;
    }

    public static OrderCancel newInstance(Order order, EOrderCancelTypeFlag type, EOrderCancelFlagCodeModel code, EOrderCancelReasonFlag reason, int totalAmount, int totalItem,
                                          int totalDelivery, int totalDiscount, int totalPg, EPaymentType paymentType, String refundName, String refundCode, String refundBank) {
        return OrderCancel.builder()
                .order(order)
                .type(type)
                .code(code)
                .reason(reason)
                .totalAmount(totalAmount)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalDiscount(totalDiscount)
                .totalPg(totalPg)
                .paymentType(paymentType)
                .refundName(refundName)
                .refundCode(refundCode)
                .refundBank(refundBank)
                .build();
    }

    public void addOrderCancelItem(OrderCancelItem orderCancelItem){
        this.orderCancelItems.add(orderCancelItem);
    }

    public void addOrderCancelComment(OrderCancelComment orderCancelComment){
        this.orderCancelComments.add(orderCancelComment);
    }


}
