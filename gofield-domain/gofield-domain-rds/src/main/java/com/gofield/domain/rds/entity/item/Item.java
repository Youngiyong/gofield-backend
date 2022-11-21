
package com.gofield.domain.rds.entity.item;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.brand.Brand;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.itemBundle.ItemBundle;
import com.gofield.domain.rds.entity.itemDetail.ItemDetail;
import com.gofield.domain.rds.entity.itemImage.ItemImage;
import com.gofield.domain.rds.entity.itemOption.ItemOption;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemDeliveryFlag;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import com.gofield.domain.rds.enums.item.EItemSpecFlag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "gender_flag", nullable = false)
    private EItemGenderFlag gender;

//    @Column(name = "spec_flag", nullable = false)
//    private EItemSpecFlag spec;

//    @Column
//    private String description;
//
//    @Column
//    private String manufactureCompany;
//
//    @Column
//    private String origin;
//    @Column
//    private LocalDateTime manufactureDate;

    @Column
    private String thumbnail;

    @Column
    private Boolean isOption;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemOption> options = new ArrayList<>();

    public void addOption(ItemOption itemOption){
        this.options.add(itemOption);
    }

    public void addImage(ItemImage itemImage){
        this.images.add(itemImage);
    }

//    private Item(ItemBundle itemBundle, String name, String itemNumber, String manufacturer, String origin, String deliveryType,)
}
