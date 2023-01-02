package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_option")
public class ItemOption extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, length = 32)
    private String itemNumber;

    @Column(nullable = false)
    private EItemOptionTypeFlag optionType;

    @Column(length = 300, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String viewName;

    @Column
    private int price;

    @Column
    private int optionPrice;

    @Column
    private Short sort;

    @Column
    private Boolean isUse;

    @Builder
    private ItemOption(Item item, String itemNumber, EItemOptionTypeFlag optionType, String name, String viewName, int price, int optionPrice, Short sort, Boolean isUse){
        this.item = item;
        this.itemNumber = itemNumber;
        this.optionType = optionType;
        this.name = name;
        this.viewName = viewName;
        this.price = price;
        this.optionPrice = optionPrice;
        this.isUse = isUse;
    }

    public static ItemOption newInstance(Item item, String itemNumber, EItemOptionTypeFlag optionType, String name, String viewName, int price, int optionPrice){
        return ItemOption.builder()
                .item(item)
                .itemNumber(itemNumber)
                .optionType(optionType)
                .name(name)
                .viewName(viewName)
                .price(price)
                .optionPrice(optionPrice)
                .isUse(true)
                .build();
    }

}
