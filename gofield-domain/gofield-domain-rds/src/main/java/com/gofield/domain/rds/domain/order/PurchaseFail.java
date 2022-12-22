package com.gofield.domain.rds.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "purchase_fail")
public class PurchaseFail {

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
    private LocalDateTime createDate;

    @Builder
    private PurchaseFail(String orderNumber, String code, String message, LocalDateTime createDate){
        this.orderNumber = orderNumber;
        this.code = code;
        this.message = message;
        this.createDate = createDate;
    }

    public static PurchaseFail newInstance(String orderNumber, String code, String message){
        return PurchaseFail.builder()
                .orderNumber(orderNumber)
                .code(code)
                .message(message)
                .createDate(LocalDateTime.now())
                .build();
    }

}