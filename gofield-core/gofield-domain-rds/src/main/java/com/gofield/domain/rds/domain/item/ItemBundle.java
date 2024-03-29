
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

    @Column
    private String description;

    @Column
    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> items = new ArrayList<>();

    @OrderBy("sort ASC")
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
    private ItemBundle(String name, Category category, Brand brand, Boolean isActive, Boolean isRecommend, String thumbnail, String description){
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.isActive = isActive;
        this.isRecommend = isRecommend;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public static ItemBundle newInstance(String name, Category category, Brand brand, Boolean isActive, Boolean isRecommend, String thumbnail, String description){
        return ItemBundle.builder()
                .name(name)
                .category(category)
                .brand(brand)
                .isActive(isActive)
                .isRecommend(isRecommend)
                .thumbnail(thumbnail)
                .description(description)
                .build();
    }

    public void update(Brand brand, Category category, String name, Boolean isRecommend, Boolean isActive, String thumbnail, String description){
        this.brand = brand != null ? brand : this.brand;
        this.category = category != null ? category : this.category;
        this.name = name != null ? name : this.name;
        this.isActive = isActive;
        this.isRecommend = isRecommend;
        this.thumbnail = thumbnail;
        this.description = description;
    }
    public void delete(){
        this.isActive = false;
        this.deleteDate = LocalDateTime.now();
        this.images.stream().forEach(i -> i.updateDeleteDate());
    }
}
