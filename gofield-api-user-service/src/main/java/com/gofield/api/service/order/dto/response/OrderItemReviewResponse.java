package com.gofield.api.service.order.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderItemReviewResponse {
    private Long id;
    private String orderNumber;
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
    private LocalDateTime finishedDate;

    @Builder
    private OrderItemReviewResponse(Long id, String orderNumber, String name, List<String> optionName, Long sellerId, String sellerName, Long bundleId, Long optionId, String thumbnail, String itemNumber, int price, int optionPrice, EItemClassificationFlag classification, EItemOptionTypeFlag optionType, int qty, int optionQty, EOrderShippingStatusFlag status, LocalDateTime finishedDate){
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

    public static OrderItemReviewResponse of(OrderItemProjection projection){
        return OrderItemReviewResponse.builder()
                .id(projection.getId())
                .orderNumber(projection.getOrderNumber())
                .name(projection.getName())
                .optionName(projection.getOptionName()==null ? null :  ObjectMapperUtils.strToObject(projection.getOptionName(), new TypeReference<List<String>>(){}))
                .sellerId(projection.getSellerId())
                .sellerName(projection.getSellerName())
                .bundleId(projection.getBundleId())
                .optionId(projection.getOptionId())
                .thumbnail(CommonUtils.makeCloudFrontUrl(projection.getThumbnail()))
                .itemNumber(projection.getItemNumber())
                .price(projection.getPrice())
                .optionPrice(projection.getOptionPrice())
                .classification(projection.getClassification())
                .optionType(projection.getOptionType())
                .qty(projection.getQty())
                .optionQty(projection.getOptionQty())
                .status(projection.getStatus())
                .finishedDate(projection.getFinishedDate())
                .build();
    }

    public static List<OrderItemReviewResponse> of(List<OrderItemProjection> list){
        return list
                .stream()
                .map(OrderItemReviewResponse::of)
                .collect(Collectors.toList());
    }
}
