
package com.gofield.domain.rds.entity.cartOption;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.cart.Cart;
import com.gofield.domain.rds.entity.itemOption.ItemOption;
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
@Table(	name = "cart_option")
public class CartOption extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id", nullable = false)
    private ItemOption itemOption;

    @Column
    private String groupTitle;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column
    private int price;

    @Column
    private int qty;
}
