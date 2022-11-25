
package com.gofield.domain.rds.entity.orderCancelComment;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.orderCancel.OrderCancel;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.order.EOrderCancelItemFlag;
import com.gofield.domain.rds.enums.order.EOrderCancelReasonFlag;
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
@Table(	name = "order_cancel_comment")
public class OrderCancelComment extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_id")
    private OrderCancel cancel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;
}
