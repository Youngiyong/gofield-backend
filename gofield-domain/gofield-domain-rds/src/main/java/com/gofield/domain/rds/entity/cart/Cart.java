
package com.gofield.domain.rds.entity.cart;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.item.EDeliveryFlag;
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
@Table(	name = "cart")
public class Cart extends BaseTimeEntity {

    @Column(name = "delivery_flag", nullable = false)
    private EDeliveryFlag delivery;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String itemNumber;

    @Column
    private int originalPrice;

    @Column
    private int offerPrice;

    @Column
    private int price;

    @Column
    private int qty;

    private Cart(EDeliveryFlag delivery, User user, String itemNumber, int originalPrice, int offerPrice, int price, int qty){
        this.delivery = delivery;
        this.user = user;
        this.itemNumber = itemNumber;
        this.originalPrice = originalPrice;
        this.offerPrice = offerPrice;
        this.price = price;
        this.qty = qty;
    }

    public static Cart newInstance(EDeliveryFlag delivery, User user, String itemNumber, int originalPrice, int offerPrice, int price, int qty){
        return new Cart(delivery, user, itemNumber, originalPrice, offerPrice, price, qty);
    }
}
