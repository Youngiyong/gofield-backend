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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String orderNumber;

    @Column
    private String paymentKey;

    @Column
    private int amount;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column
    private LocalDateTime createDate;

    @Builder
    private Purchase(String orderNumber, String paymentKey, int amount, String response, LocalDateTime createDate){
        this.orderNumber = orderNumber;
        this.paymentKey = paymentKey;
        this.amount = amount;
        this.response = response;
        this.createDate = createDate;
    }

    public static Purchase newInstance(String orderNumber, String paymentKey, int amount, String response){
        return Purchase.builder()
                .orderNumber(orderNumber)
                .paymentKey(paymentKey)
                .amount(amount)
                .response(response)
                .createDate(LocalDateTime.now())
                .build();
    }

}
