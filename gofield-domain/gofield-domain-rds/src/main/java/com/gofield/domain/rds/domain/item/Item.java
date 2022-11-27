
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.UserLikeItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item")
public class Item extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    private ItemBundle bundle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id")
    private ItemDetail detail;

    @Column
    private Long shippingTemplateId;

    @Column
    private String name;

    @Column(nullable = false, length = 32)
    private String itemNumber;

    @Column
    private int originalPrice;

    @Column
    private int price;

    @Column(name = "delivery_flag", nullable = false)
    private EItemDeliveryFlag delivery;

    @Column(name = "classification_flag", nullable = false)
    private EItemClassificationFlag classification;

    @Column
    private String thumbnail;

    @Column
    private String tags;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemStock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<UserLikeItem> user;

    @OneToMany(mappedBy = "item")
    private List<ItemHasTag> tag;


    public void addOption(ItemOption itemOption){
        this.options.add(itemOption);
    }

    public void addImage(ItemImage itemImage){
        this.images.add(itemImage);
    }

}
