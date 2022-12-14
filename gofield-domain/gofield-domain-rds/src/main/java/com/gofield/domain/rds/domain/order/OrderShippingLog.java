
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.common.EGofieldService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_shipping_log")
public class OrderShippingLog  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long orderShippingId;

    @Column
    private Long userId;

    @Column
    private EGofieldService service;

    @Column(name = "status_flag")
    private EOrderShippingStatusFlag status;

    @Column
    private LocalDateTime createDate;

    @Builder
    private OrderShippingLog(Long orderShippingId, Long userId, EGofieldService service, EOrderShippingStatusFlag status){
        this.orderShippingId = orderShippingId;
        this.userId = userId;
        this.service = service;
        this.status = status;
    }

    public static OrderShippingLog newInstance(Long orderShippingId, Long userId, EGofieldService service, EOrderShippingStatusFlag status){
        return OrderShippingLog.builder()
                .orderShippingId(orderShippingId)
                .userId(userId)
                .service(service)
                .status(status)
                .build();
    }
}
