
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.seller.Seller;
import com.gofield.domain.rds.domain.user.User;
import lombok.AccessLevel;
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
@Table(	name = "order")
public class Order extends BaseTimeEntity {

    @Column
    private Long userId;

    @Column
    private Long sellerId;

    @Column(length = 64, nullable = false)
    private String orderNumber;

    @Column
    private String pg;

    @Column
    private String impUid;

    @Column
    private int totalItem;

    @Column
    private int totalDelivery;

    @Column
    private int totalPurchase;

    @Column(nullable = false)
    private EOrderStatusFlag status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderShipping> orderShippings = new ArrayList<>();

    @Column
    private LocalDateTime cancelDate;

    @Column
    private LocalDateTime checkDate;

    @Column
    private LocalDateTime confirmDate;

    @Column
    private LocalDateTime purchaseDate;

    @Column
    private LocalDateTime finishDate;
}
