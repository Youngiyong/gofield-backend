package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderItemReviewResponse {
    private Long orderItemId;
    private String name;
    private List<String> optionName;
    private Long sellerId;
    private String sellerName;
    private Long bundleId;
    private Long optionId;
    private String thumbnail;
    private String itemNumber;
    private int price;
    private int optionPrice;
    private EItemClassificationFlag classification;
    private EItemOptionTypeFlag optionType;
    private int qty;
    private int optionQty;
    private EOrderShippingStatusFlag status;

    @Builder
    private OrderItemReviewResponse(Long orderItemId, String name, List<String> optionName, Long sellerId, String sellerName, Long bundleId, Long optionId, String thumbnail, String itemNumber, int price, int optionPrice, EItemClassificationFlag classification, EItemOptionTypeFlag optionType, int qty, int optionQty, EOrderShippingStatusFlag status){
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

    public static OrderItemReviewResponse of(Long orderItemId, String name, List<String> optionName, Long sellerId, String sellerName, Long bundleId, Long optionId, String thumbnail, String itemNumber, int price, int optionPrice, EItemClassificationFlag classification, EItemOptionTypeFlag optionType, int qty, int optionQty, EOrderShippingStatusFlag status){
        return OrderItemReviewResponse.builder()
                .orderItemId(orderItemId)
                .name(name)
                .optionName(optionName)
                .sellerId(sellerId)
                .sellerName(sellerName)
                .bundleId(bundleId)
                .optionId(optionId)
                .thumbnail(thumbnail)
                .itemNumber(itemNumber)
                .price(price)
                .optionPrice(optionPrice)
                .classification(classification)
                .optionType(optionType)
                .qty(qty)
                .optionQty(optionQty)
                .status(status)
                .build();
    }

    public static List<OrderItemReviewResponse> of(List<OrderItemProjection> list){
        return list
                .stream()
                .map(p -> OrderItemReviewResponse.of(p.getOrderItemId(), p.getName(), p.getOptionName()==null ? null : ApiUtil.strToObject(p.getOptionName(), new TypeReference<List<String>>(){}),
                                p.getSellerId(), p.getSellerName(), p.getBundleId(), p.getOptionId(), p.getThumbnail(), p.getItemNumber(),
                                p.getPrice(), p.getOptionPrice(), p.getClassification(), p.getOptionType(), p.getQty(), p.getOptionQty(), p.getStatus()))
                .collect(Collectors.toList());
    }
}
