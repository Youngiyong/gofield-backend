
package com.gofield.domain.rds.entity.item;

import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.itemBundle.ItemBundle;
import com.gofield.domain.rds.entity.itemBundleImage.ItemBundleImage;
import com.gofield.domain.rds.entity.itemImage.ItemImage;
import com.gofield.domain.rds.entity.itemOption.ItemOption;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private ItemBundle bundle;

    @Column
    private String name;

    @Column(nullable = false, length = 20)
    private String itemNumber;

    @Column(nullable = false, length = 32)
    private String manufacturer;

    @Column(nullable = false, length = 32)
    private String origin;

    @Column
    private String deliveryType;

    @Column
    private String product;

    @Column
    private String proficiency;

    @Column
    private String description;

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
