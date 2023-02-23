
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private int itemCount;

    @Column
    private Double reviewScore;

    @Column
    private int newLowestPrice;

    @Column
    private int usedLowestPrice;

    @Column
    private int lowestPrice;

    @Column
    private int highestPrice;

    @Column
    private LocalDateTime registerDate;

    @Builder
    private ItemBundleAggregation(ItemBundle itemBundle){
        this.bundle = itemBundle;
        this.reviewScore = 0.0;
        this.reviewCount = 0;
        this.itemCount = 0;
        this.lowestPrice = 0;
        this.newLowestPrice = 0;
        this.usedLowestPrice = 0;
    }

    public static ItemBundleAggregation newInstance(ItemBundle itemBundle){
        return ItemBundleAggregation.builder()
                .itemBundle(itemBundle)
                .build();
    }

    public void update(int itemCount, int newLowestPrice, int usedLowestPrice, int lowestPrice, int highestPrice){
        this.itemCount = itemCount;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }

    public void updateRegisterDate(){
        this.registerDate = LocalDateTime.now();
    }

    public void updateAggregationPrice(EItemClassificationFlag classification, int price, int lowestPrice, int highestPrice){
        if(classification.equals(EItemClassificationFlag.USED)){
            this.usedLowestPrice = price;
        } else if(classification.equals(EItemClassificationFlag.NEW)){
            this.newLowestPrice = price;
        }
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }

    public void updateReviewScore(int reviewCount, Double reviewScore){
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
    }

    public void updateItemMinusOne(){
        if(this.itemCount!=0){
            this.itemCount--;
        }
    }

    public void updateItemPlusOne(){
        this.itemCount++;
    }
}
