package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.seller.Seller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_stock")
public class ItemStock extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "status_flag", nullable = false, length = 20)
    private EItemStatusFlag status;

    @Column(name = "type_flag" , nullable = false)
    private EItemStockFlag type;

    @Column(nullable = false, length = 32)
    private String itemNumber;

    @Column
    private Long sellerId;

    @Column
    private int qty;

    public void updateOrderApprove(int qty){
        this.qty -= qty;
        if(this.qty<0 || this.qty==0){
            this.qty = 0;
            this.status = EItemStatusFlag.SOLD_OUT;
        }
    }

    public void updateOrderCancel(int qty){
        this.qty += qty;
    }

    public boolean isSale(){
        return this.status.equals(EItemStatusFlag.SALE) ? true : false;
    }
}
