package com.gofield.api.dto.res;


import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.domain.rds.domain.order.EOrderCancelItemFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.OrderCancelItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderCancelItemResponse {
    private Long id;
    private Long refId;
    private String name;
    private List<String> optionName;
    private EOrderCancelItemFlag type;
    private int qty;
    private int price;

    @Builder
    private OrderCancelItemResponse(Long id, Long refId, String name, List<String> optionName, EOrderCancelItemFlag type, int qty, int price){
        this.id = id;
        this.refId = refId;
        this.name = name;
        this.optionName = optionName;
        this.type = type;
        this.qty = qty;
        this.price = price;
    }

    public static OrderCancelItemResponse of(OrderCancelItem orderCancelItem){
        return OrderCancelItemResponse.builder()
                .id(orderCancelItem.getId())
                .refId(orderCancelItem.getRefId())
                .name(orderCancelItem.getName())
                .optionName(orderCancelItem.getOptionName()==null ? null : ApiUtil.strToObject(orderCancelItem.getOptionName(), new TypeReference<List<String>>(){}))
                .type(orderCancelItem.getType())
                .qty(orderCancelItem.getQty())
                .price(orderCancelItem.getPrice())
                .build();
    }

    public static List<OrderCancelItemResponse> of(List<OrderCancelItem> list){
        return list.stream().map(p->OrderCancelItemResponse.of(p)).collect(Collectors.toList());
    }
}
