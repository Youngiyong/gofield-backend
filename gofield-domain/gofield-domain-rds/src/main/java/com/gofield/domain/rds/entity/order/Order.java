
package com.gofield.domain.rds.entity.order;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.itemImage.ItemImage;
import com.gofield.domain.rds.entity.orderShipping.OrderShipping;
import com.gofield.domain.rds.entity.seller.Seller;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.order.EOrderStatusFlag;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(length = 20, nullable = false)
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
