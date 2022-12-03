
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column
    private Long itemOptionId;

    @Column(name = "type_flag")
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
