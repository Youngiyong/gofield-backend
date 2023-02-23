
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.ItemOption;
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
@Table(	name = "order_cancel_item")
public class OrderCancelItem extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_id")
    private OrderCancel cancel;

    @Column(name = "type_flag")
    private EOrderCancelItemFlag type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String optionName;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private int price;


    @Builder
    private OrderCancelItem(OrderCancel orderCancel, Item item, ItemOption itemOption, String name, String optionName, EOrderCancelItemFlag type, int qty, int price){
        this.cancel = orderCancel;
        this.item = item;
        this.itemOption = itemOption;
        this.name = name;
        this.optionName = optionName;
        this.type = type;
        this.qty = qty;
        this.price = price;
    }

    public static OrderCancelItem newInstance(OrderCancel orderCancel, Item item, ItemOption itemOption, String name, String optionName, EOrderCancelItemFlag type, int qty, int price){
        return OrderCancelItem.builder()
                .orderCancel(orderCancel)
                .item(item)
                .itemOption(itemOption)
                .name(name)
                .optionName(optionName)
                .type(type)
                .qty(qty)
                .price(price)
                .build();
    }
}
