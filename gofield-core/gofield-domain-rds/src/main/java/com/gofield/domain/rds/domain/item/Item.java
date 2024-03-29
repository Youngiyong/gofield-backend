package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.user.UserLikeItem;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_template_id")
    private ShippingTemplate shippingTemplate;

    @Column
    private Long sellerId;

    @Column
    private String name;

    @Column(nullable = false, length = 32)
    private String itemNumber;

    @Column
    private int originalPrice;

    @Column
    private int price;

    @Column
    private int deliveryPrice;

    @Column(name = "pickup_flag", nullable = false)
    private EItemPickupFlag pickup;

    @Column(name = "delivery_flag", nullable = false)
    private EItemDeliveryFlag delivery;

    @Column(name = "classification_flag", nullable = false)
    private EItemClassificationFlag classification;

    @Column
    private String thumbnail;

    @Column
    private String tags;

    @Column
    private Boolean isOption;

    @Column
    private Boolean isSoldOut;

    @OrderBy("sort ASC")
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemStock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemQna> qnas = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOptionGroup> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<UserLikeItem> user;

    @OneToMany(mappedBy = "item")
    private List<ItemHasTag> tag;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private Item(ItemBundle itemBundle, Brand brand, Category category, ItemDetail itemDetail, ShippingTemplate shippingTemplate, Long sellerId,
                 String itemNumber, String name, int price, int deliveryPrice, EItemPickupFlag pickup, EItemDeliveryFlag delivery, EItemClassificationFlag classification, String thumbnail, String tags, Boolean isOption){
        this.bundle = itemBundle;
        this.brand = brand;
        this.category = category;
        this.detail = itemDetail;
        this.shippingTemplate = shippingTemplate;
        this.sellerId = sellerId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.pickup = pickup;
        this.delivery = delivery;
        this.classification = classification;
        this.thumbnail = thumbnail;
        this.tags = tags;
        this.isOption = isOption;
    }

    public static Item newUsedItemInstance(ItemBundle itemBundle, Brand brand, Category category, ItemDetail itemDetail, ShippingTemplate shippingTemplate, Long sellerId,  String thumbnail,
                          String itemNumber, String name, int price, int deliveryPrice, EItemPickupFlag pickup, EItemDeliveryFlag delivery, String tags){
        return Item.builder()
                .itemBundle(itemBundle)
                .brand(brand)
                .category(category)
                .itemDetail(itemDetail)
                .shippingTemplate(shippingTemplate)
                .sellerId(sellerId)
                .itemNumber(itemNumber)
                .name(name)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .pickup(pickup)
                .delivery(delivery)
                .classification(EItemClassificationFlag.USED)
                .thumbnail(thumbnail)
                .tags(tags)
                .isOption(false)
                .build();
    }

    public static Item newNewItemInstance(ItemBundle itemBundle, Brand brand, Category category, ItemDetail itemDetail, ShippingTemplate shippingTemplate, Long sellerId,  String thumbnail,
                                           String itemNumber, String name, int price, int deliveryPrice, EItemPickupFlag pickup, EItemDeliveryFlag delivery, String tags, Boolean isOption){
        return Item.builder()
                .itemBundle(itemBundle)
                .brand(brand)
                .category(category)
                .itemDetail(itemDetail)
                .shippingTemplate(shippingTemplate)
                .sellerId(sellerId)
                .itemNumber(itemNumber)
                .name(name)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .pickup(pickup)
                .delivery(delivery)
                .classification(EItemClassificationFlag.NEW)
                .thumbnail(thumbnail)
                .tags(tags)
                .isOption(isOption)
                .build();
    }

    public void addOption(ItemOption itemOption){
        this.options.add(itemOption);
    }

    public void addImage(ItemImage itemImage){
        this.images.add(itemImage);
    }

    public void addOptionGroup(ItemOptionGroup optionGroup){
        this.optionGroups.add(optionGroup);
    }

    public void addStock(ItemStock itemStock){
        this.stocks.add(itemStock);
    }

    public void addQna(ItemQna itemQna) { this.qnas.add(itemQna); }

    public void deleteAllOptions(List<ItemOption> options){
        options.stream().forEach(itemOption -> itemOption.delete());
    }

    public void removeAllOptionGroups(List<ItemOptionGroup> optionGroups){
        optionGroups.removeAll(optionGroups);
    }

    public void delete(){
        this.deleteDate = LocalDateTime.now();
        this.images.stream().forEach(i -> i.delete());
        this.detail.delete();
    }

    public void updateOptionTrue(){
        this.isOption = true;
    }

    public void updateOptionFalse(){
        this.isOption = false;
    }

    public void update(Boolean isOption){
        this.isOption = isOption;
    }

    public void update(ShippingTemplate shippingTemplate){
        this.shippingTemplate = shippingTemplate;
    }

    public void update(String name, int price, int deliveryPrice, EItemDeliveryFlag delivery, EItemPickupFlag pickup, String tags, Boolean isOption){
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.delivery = delivery;
        this.pickup = pickup;
        this.tags = tags;
        this.isOption = isOption;
    }

    public void update(ItemBundle bundle, String name, int price, int deliveryPrice, EItemDeliveryFlag delivery, EItemPickupFlag pickup, String tags, Boolean isOption, String thumbnail){
        this.bundle = bundle;
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.delivery = delivery;
        this.pickup = pickup;
        this.tags = tags;
        this.isOption = isOption;
        this.thumbnail = thumbnail;
    }

    public void update(String name, int price, int deliveryPrice, EItemDeliveryFlag delivery, EItemPickupFlag pickup, String tags){
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.delivery = delivery;
        this.pickup = pickup;
        this.tags = tags;
    }

    public void update(ItemBundle bundle, String name, int price, int deliveryPrice, EItemDeliveryFlag delivery, EItemPickupFlag pickup, String tags){
        this.bundle = bundle;
        this.name = name;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.delivery = delivery;
        this.pickup = pickup;
        this.tags = tags;
    }

    public void updateThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public void updateIsSoldOut(){
        this.isSoldOut = true;
    }

    public void updateNotSoldOut(){
        this.isSoldOut = false;
    }
}
