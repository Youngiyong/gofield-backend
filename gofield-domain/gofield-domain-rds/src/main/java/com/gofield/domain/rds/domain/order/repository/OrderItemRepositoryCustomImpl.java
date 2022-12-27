package com.gofield.domain.rds.domain.order.repository;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import com.gofield.domain.rds.domain.order.projection.QOrderItemProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItem.item;
import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderItem.orderItem;
import static com.gofield.domain.rds.domain.order.QOrderItemOption.orderItemOption;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;
import static com.gofield.domain.rds.domain.seller.QSeller.seller;
import static com.gofield.domain.rds.domain.item.QShippingTemplate.shippingTemplate;

@RequiredArgsConstructor
public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public OrderItem findByOrderItemId(Long id) {
        return jpaQueryFactory
                .select(orderItem)
                .from(orderItem)
                .innerJoin(orderItem.orderShipping, orderShipping).fetchJoin()
                .where(orderItem.id.eq(id))
                .fetchFirst();
    }

    @Override
    public OrderItem findByOrderItemIdAndUserId(Long id, Long userId) {
        return jpaQueryFactory
                .select(orderItem)
                .from(orderItem)
                .innerJoin(orderItem.orderShipping, orderShipping).fetchJoin()
                .innerJoin(order).on(orderItem.order.id.eq(order.id))
                .where(order.userId.eq(userId), orderItem.id.eq(id))
                .fetchFirst();
    }

    @Override
    public OrderItem findByOrderItemIdAndUserIdFetch(Long id, Long userId) {
        return jpaQueryFactory
                .select(orderItem)
                .from(orderItem)
                .innerJoin(orderItem.order, order).fetchJoin()
                .innerJoin(orderItem.orderShipping, orderShipping).fetchJoin()
                .innerJoin(orderItem.item, item).fetchJoin()
                .leftJoin(orderItem.orderItemOption, orderItemOption).fetchJoin()
                .leftJoin(item.shippingTemplate, shippingTemplate).fetchJoin()
                .where(orderItem.id.eq(id), order.userId.eq(userId))
                .fetchOne();
    }

    @Override
    public OrderItem findByOrderItemIdFetch(Long id) {
        return jpaQueryFactory
                .select(orderItem)
                .from(orderItem)
                .innerJoin(orderItem.order, order).fetchJoin()
                .innerJoin(orderItem.orderShipping, orderShipping).fetchJoin()
                .innerJoin(orderItem.item, item).fetchJoin()
                .leftJoin(orderItem.orderItemOption, orderItemOption).fetchJoin()
                .leftJoin(item.shippingTemplate, shippingTemplate).fetchJoin()
                .where(orderItem.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<OrderItemProjection> findAllByUserIdAndShippingStatusShippingComplete(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(new QOrderItemProjection(
                        orderItem.id,
                        orderShipping.orderNumber,
                        orderItem.name,
                        orderItemOption.name,
                        orderItem.sellerId,
                        seller.name,
                        item.bundle.id,
                        orderItemOption.itemOptionId,
                        item.thumbnail,
                        orderItem.itemNumber,
                        orderItem.price,
                        orderItemOption.price,
                        item.classification,
                        orderItemOption.optionType,
                        orderItem.qty,
                        orderItemOption.qty,
                        orderShipping.status,
                        orderShipping.finishedDate))
                .from(order)
                .innerJoin(orderItem)
                .on(order.id.eq(orderItem.order.id))
                .innerJoin(orderShipping)
                .on(orderItem.orderShipping.id.eq(orderShipping.id))
                .innerJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .leftJoin(seller)
                .on(orderItem.sellerId.eq(seller.id))
                .leftJoin(orderItemOption)
                .on(orderItem.orderItemOption.id.eq(orderItemOption.id))
                .where(order.userId.eq(userId), orderShipping.status.eq(EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE).or(orderShipping.status.eq(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderItem.createDate.desc())
                .fetch();
    }
}
