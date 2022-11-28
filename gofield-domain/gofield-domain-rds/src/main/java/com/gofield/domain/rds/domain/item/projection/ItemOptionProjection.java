package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class ItemOptionProjection {

    private final Long id;
    private final Long itemId;
    private final String itemNumber;
    private final String name;
    private final EItemOptionTypeFlag optionType;
    private final EItemStatusFlag status;
    private final int price;

    private final int optionPrice;
    private final int qty;
    private final Short sort;
    private final Boolean isUse;
    private final LocalDateTime createDate;


    @QueryProjection
    public ItemOptionProjection(Long id, Long itemId, String itemNumber, String name, EItemOptionTypeFlag optionType, EItemStatusFlag status, int price, int optionPrice, int qty, Short sort, Boolean isUse, LocalDateTime createDate) {
        this.id = id;
        this.itemId = itemId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.optionType = optionType;
        this.status = status;
        this.price = price;
        this.optionPrice = optionPrice;
        this.qty = qty;
        this.sort = sort;
        this.isUse = isUse;
        this.createDate = createDate;
    }
}
