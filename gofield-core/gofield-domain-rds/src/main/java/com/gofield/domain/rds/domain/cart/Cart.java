
package com.gofield.domain.rds.domain.cart;


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
@Table(	name = "cart")
public class Cart extends BaseTimeEntity {

    @Column
    private Long userId;

    @Column
    private String itemNumber;

    @Column
    private int price;

    @Column
    private int qty;

    @Column
    private Boolean isOrder;

    @Column
    private Boolean isNow;

    private Cart(Long userId, String itemNumber, int price, int qty, Boolean isOrder,  Boolean isNow){
        this.userId = userId;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.isOrder = isOrder;
        this.isNow = isNow;
    }

    public static Cart newInstance(Long userId,  String itemNumber, int price, int qty, Boolean isOrder, Boolean isNow){
        return new Cart(userId, itemNumber, price, qty, isOrder, isNow);
    }

    public void updateQty(int qty, int stockQty){
        if(stockQty>qty){
            this.isOrder = true;
        } else {
            this.isOrder = false;
        }
        this.qty = qty;
    }

    public void updateOne(int stockQty){
        if(stockQty>qty){
            this.isOrder = true;
        } else {
            this.isOrder = false;
        }
        this.qty+=1;
    }

}
