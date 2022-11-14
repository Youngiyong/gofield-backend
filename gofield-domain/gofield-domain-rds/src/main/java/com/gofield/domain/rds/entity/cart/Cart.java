
package com.gofield.domain.rds.entity.cart;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.item.EItemDeliveryFlag;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String itemNumber;

    @Column
    private int originalPrice;

    @Column
    private int price;

    @Column
    private int qty;

    private Cart(User user, String itemNumber, int originalPrice, int price, int qty){
        this.user = user;
        this.itemNumber = itemNumber;
        this.originalPrice = originalPrice;
        this.price = price;
        this.qty = qty;
    }

    public static Cart newInstance(User user, String itemNumber, int originalPrice, int price, int qty){
        return new Cart(user, itemNumber, originalPrice, price, qty);
    }

}
