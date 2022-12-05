
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
@Table(	name = "order_shipping_address")
public class OrderShippingAddress extends BaseTimeEntity {

    @Column(length = 64, nullable = false)
    private String orderNumber;

    @Column
    private String name;

    @Column
    private String tel;

    @Column
    private String zipCode;

    @Column
    private String address;

    @Column
    private String addressExtra;

    @Column
    private String comment;

    @Builder
    private OrderShippingAddress(String orderNumber, String name, String tel, String zipCode, String address, String addressExtra, String comment){
        this.orderNumber = orderNumber;
        this.name = name;
        this.tel = tel;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.comment = comment;
    }

    public static OrderShippingAddress newInstance(String orderNumber, String name, String tel, String zipCode, String address, String addressExtra, String comment){
        return OrderShippingAddress.builder()
                .orderNumber(orderNumber)
                .name(name)
                .tel(tel)
                .zipCode(zipCode)
                .address(address)
                .addressExtra(addressExtra)
                .comment(comment)
                .build();
    }
}
