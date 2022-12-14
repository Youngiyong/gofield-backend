
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
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
@Table(	name = "item_aggregation")
public class ItemAggregation extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private int hitCount;

    @Column
    private int likeCount;

    @Column
    private int reviewCount;

    @Column
    private int orderCount;

    @Builder
    private ItemAggregation(Item item){
        this.item = item;
        this.hitCount = 0;
        this.likeCount = 0;
        this.reviewCount = 0;
        this.orderCount = 0;
    }

    public static ItemAggregation newInstance(Item item){
        return ItemAggregation.builder()
                .item(item)
                .build();
    }
}
