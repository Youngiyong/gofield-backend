
package com.gofield.domain.rds.entity.order;


import com.gofield.domain.rds.entity.BaseTimeEntity;
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

    @Column
    private int totalItem;

    @Column
    private int totalDelivery;

    @Column
    private int subtotalDelivery;

    @Column
    private int totalDiscount;

    @Column
    private int totalPoint;

    @Column
    private int totalPurchase;

    @Column
    private EOrderStatusFlag status;

    @Column
    private LocalDateTime confirmDate;

    @Column
    private LocalDateTime purchaseDate;


}
