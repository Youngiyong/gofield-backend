package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
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

    @Column(nullable = false, length = 32)
    private String itemNumber;

    @Column
    private EItemOptionTypeFlag optionType;

    @Column(name = "group_title")
    private String groupTitle;

    @Column(name = "option_group")
    private String group;

    @Column(name = "price_group")
    private String priceGroup;

    @Column
    private Boolean isEssential;
}
