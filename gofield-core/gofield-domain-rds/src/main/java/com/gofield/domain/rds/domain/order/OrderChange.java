package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_change")
public class OrderChange extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping orderShipping;

    @Column(name = "code_flag", nullable = false)
    private EOrderChangeFlagCodeModel code;

    @Column(name = "status_flag")
    private EOrderChangeStatusFlag status;

    @Column(name = "reason_flag")
    private EOrderChangeReasonFlag reason;

    @Column(columnDefinition = "TEXT")
    private String content;


    @Column
    private String carrier;

    @Column
    private String trackingNumber;

    @Column
    private String recallCarrier;

    @Column
    private String recallTrackingNumber;

    @Column
    private LocalDateTime resendDate;

    @Column
    private LocalDateTime recalledDate;

    @OneToMany(mappedBy = "change", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderChangeComment> orderChangeComments = new ArrayList<>();

    @Builder
    private OrderChange(Order order, OrderShipping orderShipping, EOrderChangeFlagCodeModel code,
                        EOrderChangeStatusFlag status, EOrderChangeReasonFlag reason,  String content){
        this.order = order;
        this.orderShipping = orderShipping;
        this.code = code;
        this.status = status;
        this.reason = reason;
        this.content = content;
    }

    public static OrderChange newInstance(Order order, OrderShipping orderShipping, EOrderChangeFlagCodeModel code,
                                          EOrderChangeStatusFlag status, EOrderChangeReasonFlag reason,  String content) {
        return OrderChange.builder()
                .order(order)
                .orderShipping(orderShipping)
                .code(code)
                .status(status)
                .reason(reason)
                .content(content)
                .build();
    }


    public void addOrderChangeComment(OrderChangeComment orderChangeComment){
        this.orderChangeComments.add(orderChangeComment);
    }


}
