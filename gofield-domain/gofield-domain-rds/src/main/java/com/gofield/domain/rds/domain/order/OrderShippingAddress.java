
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(length = 20, nullable = false)
    private String orderNumber;

    @Column
    private String receiptName;

    @Column
    private String receiptCp;

    @Column
    private String zipCode;

    @Column
    private String address;

    @Column
    private String addressExtra;

    @Column
    private String jibunAddress;

    @Column
    private String comment;
}
