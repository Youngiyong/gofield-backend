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
}
