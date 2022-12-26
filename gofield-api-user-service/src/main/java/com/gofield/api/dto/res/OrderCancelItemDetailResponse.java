package com.gofield.api.dto.res;


import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.model.Constants;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.order.EOrderCancelItemFlag;
import com.gofield.domain.rds.domain.order.OrderCancelItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderCancelItemDetailResponse {
    private Long id;
    private Long itemId;
    private Long itemOptionId;
    private String name;
    private List<String> optionName;
    private String itemNumber;
    private String thumbnail;
    private EOrderCancelItemFlag type;
    private int qty;
    private int price;


    @Builder
    private OrderCancelItemDetailResponse(Long id, Long itemId, Long itemOptionId, String name, List<String> optionName, String itemNumber,  String thumbnail,  EOrderCancelItemFlag type, int qty, int price){
        this.id = id;
        this.itemId = itemId;
        this.itemOptionId = itemOptionId;
        this.name = name;
        this.optionName = optionName;
        this.itemNumber = itemNumber;
        this.thumbnail = thumbnail;
        this.type = type;
        this.qty = qty;
        this.price = price;
    }

    public static OrderCancelItemDetailResponse of(OrderCancelItem orderCancelItem){
        Item item = orderCancelItem.getItem();
        ItemOption itemOption = orderCancelItem.getItemOption();
        Long itemId = item == null ? null : item.getId();
        Long itemOptionId = itemOption == null ? null : itemOption.getId();
        String itemNumber = itemOption==null ? item.getItemNumber() : itemOption.getItemNumber();
        String thumbnail = itemOption==null ? item.getThumbnail() : itemOption.getItem().getThumbnail();

        return OrderCancelItemDetailResponse.builder()
                .id(orderCancelItem.getId())
                .name(orderCancelItem.getName())
                .optionName(orderCancelItem.getOptionName()==null ? null : ApiUtil.strToObject(orderCancelItem.getOptionName(), new TypeReference<List<String>>(){}))
                .thumbnail(thumbnail)
                .itemNumber(itemNumber)
                .itemId(itemId)
                .itemOptionId(itemOptionId)
                .thumbnail(Constants.CDN_URL.concat(thumbnail).concat(Constants.RESIZE_200x200))
                .type(orderCancelItem.getType())
                .qty(orderCancelItem.getQty())
                .price(orderCancelItem.getPrice())
                .build();
    }

    public static List<OrderCancelItemDetailResponse> of(List<OrderCancelItem> list){
        return list.stream().map(p-> OrderCancelItemDetailResponse.of(p)).collect(Collectors.toList());
    }
}
