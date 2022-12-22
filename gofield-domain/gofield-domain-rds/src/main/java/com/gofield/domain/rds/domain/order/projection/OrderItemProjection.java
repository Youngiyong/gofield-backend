package com.gofield.domain.rds.domain.order.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderItemProjection {

    private final Long orderItemId;
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

    @QueryProjection
    public OrderItemProjection(Long orderItemId, String name, String optionName, Long sellerId, String sellerName, Long bundleId, Long optionId, String thumbnail, String itemNumber, int price, int optionPrice, EItemClassificationFlag classification, EItemOptionTypeFlag optionType, int qty, int optionQty, EOrderShippingStatusFlag status) {
        this.orderItemId = orderItemId;
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
    }
}