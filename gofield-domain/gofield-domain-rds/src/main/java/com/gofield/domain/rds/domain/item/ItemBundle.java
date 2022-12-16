
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
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
@Table(	name = "item_bundle")
public class ItemBundle extends BaseTimeEntity {

    @Column
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column
    private Short sort;

    @Column
    private Boolean isActive;

    @Column
    private Boolean isRecommend;

    @Column
    private String thumbnail;

    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> items = new ArrayList<>();
    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemBundleImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemBundleReview> reviews = new ArrayList<>();
    public void addBundleImage(ItemBundleImage bundleImage){
        this.images.add(bundleImage);
    }

    public void addBundleReview(ItemBundleReview bundleReview){
        this.reviews.add(bundleReview);
    }

    @Builder
    private ItemBundle(String name, Category category, Brand brand, Boolean isActive, Boolean isRecommend, String thumbnail){
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.isActive = isActive;
        this.isRecommend = isRecommend;
        this.thumbnail = thumbnail;
    }

    public static ItemBundle newInstance(String name, Category category, Brand brand, Boolean isActive, Boolean isRecommend, String thumbnail){
        return ItemBundle.builder()
                .name(name)
                .category(category)
                .brand(brand)
                .isActive(isActive)
                .isRecommend(isRecommend)
                .thumbnail(thumbnail)
                .build();
    }

    public void update(Brand brand, Category category, String name, Boolean isRecommend, String thumbnail){
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.isRecommend = isRecommend;
        this.thumbnail =  thumbnail != null ? thumbnail : this.thumbnail;
    }
    public void updateInActive(){
        this.isActive = false;
    }
}
