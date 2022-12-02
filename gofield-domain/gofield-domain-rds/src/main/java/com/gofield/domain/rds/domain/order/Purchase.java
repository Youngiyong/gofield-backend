package com.gofield.domain.rds.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private String message;

    @Column
    private String orderNumber;

    @Column
    private String paymentKey;

    @Column
    private int amount;

    @Column
    private Boolean isSuccess;

    @Column
    private LocalDateTime createDate;

    @Builder
    private Purchase(String orderNumber, String code, String message, String paymentKey, int amount, Boolean isSuccess){
        this.orderNumber = orderNumber;
        this.code = code;
        this.message = message;
        this.paymentKey = paymentKey;
        this.amount = amount;
        this.isSuccess = isSuccess;
    }

    public static Purchase newFailInstance(String orderNumber, String code, String message){
        return Purchase.builder()
                .orderNumber(orderNumber)
                .code(code)
                .message(message)
                .isSuccess(false)
                .build();
    }

    public static Purchase newSuccessInstance(String orderNumber, String paymentKey, int amount){
        return Purchase.builder()
                .orderNumber(orderNumber)
                .paymentKey(paymentKey)
                .amount(amount)
                .isSuccess(true)
                .build();
    }
}
