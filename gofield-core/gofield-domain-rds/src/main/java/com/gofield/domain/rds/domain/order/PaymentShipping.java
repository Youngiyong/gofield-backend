package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "payment_shipping")
public class PaymentShipping extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping orderShipping;

    @Column
    private Long userId;

    @Column
    private String paymentKey;

    @Column
    private int totalAmount;

    @Column
    private String paymentCompany;

    @Column
    private String paymentType;

    @Column
    private String cardNumber;

    @Column
    private String cardType;

    @Column
    private int installmentPlanMonth;


    @Builder
    private PaymentShipping(Order order, OrderShipping orderShipping, Long userId, String paymentKey, int totalAmount, String paymentCompany, String paymentType, String cardNumber, String cardType, int installmentPlanMonth){
        this.order = order;
        this.orderShipping = orderShipping;
        this.userId = userId;
        this.paymentKey = paymentKey;
        this.totalAmount = totalAmount;
        this.paymentCompany = paymentCompany;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.installmentPlanMonth = installmentPlanMonth;
    }

    public static PaymentShipping newInstance(Order order, OrderShipping orderShipping, Long userId, String paymentKey, int totalAmount, String paymentCompany, String paymentType, String cardNumber, String cardType, int installmentPlanMonth){
        return PaymentShipping.builder()
                .order(order)
                .orderShipping(orderShipping)
                .userId(userId)
                .paymentKey(paymentKey)
                .totalAmount(totalAmount)
                .paymentCompany(paymentCompany)
                .paymentType(paymentType)
                .cardNumber(cardNumber)
                .cardType(cardType)
                .installmentPlanMonth(installmentPlanMonth)
                .build();
    }

}
