package com.gofield.api.service.order.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.order.EOrderItemStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long itemId;
    private Long itemOptionId;
    private String orderItemNumber;
    private String itemNumber;
    private String name;
    private List<String> optionName;
    private EItemClassificationFlag classification;
    private String thumbnail;
    private EOrderItemStatusFlag status;
    private Boolean isReview;
    private int price;
    private int qty;
    private LocalDateTime returnWithdrawDate;

    @Builder
    private OrderItemResponse(Long id, Long itemId, Long itemOptionId, String orderItemNumber,  String itemNumber,
                              String name, List<String> optionName, EItemClassificationFlag classification, String thumbnail, EOrderItemStatusFlag status, Boolean isReview,
                              int price, int qty, LocalDateTime returnWithdrawDate){
        this.id = id;
        this.itemId = itemId;
        this.itemOptionId = itemOptionId;
        this.orderItemNumber = orderItemNumber;
        this.itemNumber = itemNumber;
        this.name = name;
        this.optionName = optionName;
        this.classification = classification;
        this.thumbnail = thumbnail;
        this.status = status;
        this.isReview = isReview;
        this.price = price;
        this.qty = qty;
        this.returnWithdrawDate = returnWithdrawDate;
    }

    public static OrderItemResponse of(OrderItem orderItem){
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .itemId(orderItem.getOrder().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .orderItemNumber(orderItem.getOrderItemNumber())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null : ObjectMapperUtils.strToObject(orderItem.getOrderItemOption().getName(), new TypeReference<List<String>>(){}))
                .classification(orderItem.getItem().getClassification())
                .status(orderItem.getStatus())
                .isReview(orderItem.getIsReview())
                .price(orderItem.getOrderItemOption()==null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice())
                .qty(orderItem.getOrderItemOption()==null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty())
                .thumbnail(CommonUtils.makeCloudFrontUrl(orderItem.getItem().getThumbnail()))
                .returnWithdrawDate(orderItem.getReturnWithdrawDate())
                .build();
    }

    public static List<OrderItemResponse> of(List<OrderItem> list) {
        return list
                .stream()
                .map(OrderItemResponse::of)
                .collect(Collectors.toList());

    }
}
