package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.order.EOrderItemStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    private OrderItemResponse(Long id, Long itemId, Long itemOptionId, String orderItemNumber,  String itemNumber,
                              String name, List<String> optionName, EItemClassificationFlag classification, String thumbnail, EOrderItemStatusFlag status, Boolean isReview,
                              int price, int qty){
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
    }

    public static OrderItemResponse of(OrderItem orderItem){
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .itemId(orderItem.getOrder().getId())
                .itemOptionId(orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getItemOptionId())
                .orderItemNumber(orderItem.getOrderItemNumber())
                .itemNumber(orderItem.getItemNumber())
                .name(orderItem.getName())
                .optionName(orderItem.getOrderItemOption()==null ? null : ApiUtil.strToObject(orderItem.getOrderItemOption().getName(), new TypeReference<List<String>>(){}))
                .classification(orderItem.getItem().getClassification())
                .status(orderItem.getStatus())
                .isReview(orderItem.getIsReview())
                .price(orderItem.getOrderItemOption()==null ? orderItem.getPrice() : orderItem.getOrderItemOption().getPrice())
                .qty(orderItem.getOrderItemOption()==null ? orderItem.getQty() : orderItem.getOrderItemOption().getQty())
                .thumbnail(Constants.CDN_URL.concat(orderItem.getItem().getThumbnail()).concat(Constants.RESIZE_200x200))
                .build();
    }

    public static OrderItemResponse of(Long id, Long itemId, Long itemOptionId, String orderItemNumber, String itemNumber,
                                       String name, List<String> optionName, EItemClassificationFlag classification, String thumbnail, EOrderItemStatusFlag status, Boolean isReview,
                                       int price, int qty){
        return OrderItemResponse.builder()
                .id(id)
                .itemId(itemId)
                .itemOptionId(itemOptionId)
                .orderItemNumber(orderItemNumber)
                .itemNumber(itemNumber)
                .name(name)
                .optionName(optionName)
                .classification(classification)
                .thumbnail(Constants.CDN_URL.concat(thumbnail).concat(Constants.RESIZE_200x200))
                .status(status)
                .isReview(isReview)
                .price(price)
                .qty(qty)
                .build();
    }

    public static List<OrderItemResponse> of(List<OrderItem> list){
        return list
                .stream()
                .map(p -> OrderItemResponse.of(p.getId(), p.getItem().getId(), p.getOrderItemOption()==null ? null : p.getOrderItemOption().getItemOptionId(),
                               p.getOrderItemNumber(), p.getItemNumber(), p.getName(), p.getOrderItemOption()==null ? null : ApiUtil.strToObject(p.getOrderItemOption().getName(), new TypeReference<List<String>>(){}),
                    p.getItem().getClassification(), p.getItem().getThumbnail(), p.getStatus(), p.getIsReview(), p.getOrderItemOption()==null ? p.getPrice() :  p.getOrderItemOption().getPrice(), p.getOrderItemOption()==null ? p.getQty() : p.getOrderItemOption().getQty()))
                .collect(Collectors.toList());
    }
}
