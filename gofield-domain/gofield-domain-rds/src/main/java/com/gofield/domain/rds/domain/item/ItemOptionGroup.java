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
@Table(	name = "item_option_group")
public class ItemOptionGroup extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column
    private EItemOptionTypeFlag optionType;

    @Column(length = 128)
    private String groupTitle;

    @Column(name = "option_group")
    private String optionGroup;

    @Column(name = "price_group")
    private String priceGroup;

    @Column
    private Boolean isEssential;

    @Builder
    private ItemOptionGroup(Item item, EItemOptionTypeFlag optionType, String groupTitle, String optionGroup, String priceGroup, Boolean isEssential){
        this.item = item;
        this.optionType = optionType;
        this.groupTitle = groupTitle;
        this.optionGroup = optionGroup;
        this.priceGroup = priceGroup;
        this.isEssential = isEssential;
    }

    public static ItemOptionGroup newInstance(Item item, EItemOptionTypeFlag optionType, String groupTitle, String optionGroup, String priceGroup){
        return ItemOptionGroup
                .builder()
                .item(item)
                .optionType(optionType)
                .groupTitle(groupTitle)
                .optionGroup(optionGroup)
                .isEssential(true)
                .build();
    }
    public void update(EItemOptionTypeFlag optionType, String groupTitle, String optionGroup){
        this.optionType = optionType;
        this.groupTitle = groupTitle;
        this.optionGroup = optionGroup;
    }
}
