
package com.gofield.domain.rds.entity.orderItemOption;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.itemOption.ItemOption;
import com.gofield.domain.rds.entity.orderItem.OrderItem;
import com.gofield.domain.rds.enums.item.EItemOptionTypeFlag;
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
@Table(	name = "order_shipping_item")
public class OrderItemOption extends BaseTimeEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orde_item_option_id")
    private OrderItem orderItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_id")
    private ItemOption option;

    @Column
    private EItemOptionTypeFlag optionType;

    @Column
    private String name;

    @Column
    private int qty;

    @Column
    private int cancelQty;

    @Column
    private int price;

}
