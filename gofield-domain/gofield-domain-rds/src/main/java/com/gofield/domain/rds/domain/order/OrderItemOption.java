package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
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
@Table(	name = "order_item_option")
public class OrderItemOption extends BaseTimeEntity {

    @Column
    private Long itemOptionId;

    @Column(name = "type_flag")
    private EItemOptionTypeFlag optionType;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column
    private int qty;

    @Column
    private int cancelQty;

    @Column
    private int price;

    @Builder
    private OrderItemOption(Long itemOptionId, EItemOptionTypeFlag optionType, String name, int qty,  int price){
        this.itemOptionId = itemOptionId;
        this.optionType = optionType;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public static OrderItemOption newInstance(Long itemOptionId, EItemOptionTypeFlag optionType, String name, int qty, int price){
        return OrderItemOption.builder()
                .itemOptionId(itemOptionId)
                .optionType(optionType)
                .name(name)
                .qty(qty)
                .price(price)
                .build();
    }
}
