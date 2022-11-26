
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_bundle_aggregation")
public class ItemBundleAggregation extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private ItemBundle bundle;

    @Column
    private int reviewCount;

    @Column
    private Double reviewScore;

    @Column
    private int newLowestPrice;

    @Column
    private int usedLowestPrice;
}
