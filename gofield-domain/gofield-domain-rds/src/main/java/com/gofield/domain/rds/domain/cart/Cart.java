
package com.gofield.domain.rds.domain.cart;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.user.User;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column
    private String itemNumber;

    @Column
    private int price;

    @Column
    private int qty;

    @Column
    private Boolean isNow;

    private Cart(User user, Item item, String itemNumber, int price, int qty, Boolean isNow){
        this.user = user;
        this.item = item;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.isNow = isNow;
    }

    public static Cart newInstance(User user, Item item, String itemNumber, int price, int qty, Boolean isNow){
        return new Cart(user, item, itemNumber, price, qty, isNow);
    }

}
