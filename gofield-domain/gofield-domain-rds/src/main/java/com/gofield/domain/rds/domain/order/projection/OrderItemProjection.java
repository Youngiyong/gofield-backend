package com.gofield.domain.rds.domain.order.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class OrderItemProjection {
    private final Long id;
    private final String orderNumber;
    private final String name;
    private final String optionName;
    private final Long sellerId;
    private final String sellerName;
    private final Long bundleId;
    private final Long optionId;
    private final String thumbnail;
    private final String itemNumber;
    private final int price;
    private final int optionPrice;
    private final EItemClassificationFlag classification;
    private final EItemOptionTypeFlag optionType;
    private final int qty;
    private final int optionQty;
    private final EOrderShippingStatusFlag status;
    private final LocalDateTime finishedDate;

    @QueryProjection
    public OrderItemProjection(Long id, String orderNumber, String name, String optionName, Long sellerId, String sellerName, Long bundleId, Long optionId, String thumbnail, String itemNumber, int price, int optionPrice, EItemClassificationFlag classification, EItemOptionTypeFlag optionType, int qty, int optionQty, EOrderShippingStatusFlag status, LocalDateTime finishedDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.name = name;
        this.optionName = optionName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.bundleId = bundleId;
        this.optionId = optionId;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.price = price;
        this.optionPrice = optionPrice;
        this.classification = classification;
        this.optionType = optionType;
        this.qty = qty;
        this.optionQty = optionQty;
        this.status = status;
        this.finishedDate = finishedDate;
    }
}
