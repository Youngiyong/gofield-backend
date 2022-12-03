package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.projection.ItemOrderSheetProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemOrderSheetResponse {

    private Long id;
    private String brandName;
    private String name;
    private List<String> optionName;
    private String thumbnail;
    private String itemNumber;
    private int price;
    private int qty;
    private int deliveryPrice;

    @Builder
    private ItemOrderSheetResponse(Long id, String brandName, String name, List<String> optionName, String thumbnail,
                                   String itemNumber, int price, int qty, int deliveryPrice){
        this.id = id;
        this.brandName = brandName;
        this.name = name;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.deliveryPrice = deliveryPrice;
    }

    /*
    ToDo: 배송비는 우션 0원처리
     */
    public static ItemOrderSheetResponse of(Long id, String brandName, String name, String optionName, String thumbnail,
                                            String itemNumber, int price, int qty, int deliveryPrice){

        try {
            return ItemOrderSheetResponse.builder()
                    .id(id)
                    .brandName(brandName)
                    .name(name)
                    .optionName(optionName==null ? null : new ObjectMapper().readValue(optionName, new TypeReference<List<String>>(){}))
                    .thumbnail(thumbnail)
                    .itemNumber(itemNumber)
                    .price(price)
                    .qty(qty)
                    .deliveryPrice(deliveryPrice)
                    .build();
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }


}
