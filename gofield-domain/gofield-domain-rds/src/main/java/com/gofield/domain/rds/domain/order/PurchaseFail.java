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
    private PurchaseFail(String orderNumber, String code, String message){
        this.orderNumber = orderNumber;
        this.code = code;
        this.message = message;
    }

    public static PurchaseFail newInstance(String orderNumber, String code, String message){
        return PurchaseFail.builder()
                .orderNumber(orderNumber)
                .code(code)
                .message(message)
                .build();
    }

}
