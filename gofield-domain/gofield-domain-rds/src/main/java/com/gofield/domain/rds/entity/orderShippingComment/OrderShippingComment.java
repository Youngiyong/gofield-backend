
package com.gofield.domain.rds.entity.orderShippingComment;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.order.Order;
import com.gofield.domain.rds.entity.seller.Seller;
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
@Table(	name = "order_shipping_comment")
public class OrderShippingComment extends BaseTimeEntity {



    @Column(length = 20, nullable = false)
    private String orderNumber;

}
