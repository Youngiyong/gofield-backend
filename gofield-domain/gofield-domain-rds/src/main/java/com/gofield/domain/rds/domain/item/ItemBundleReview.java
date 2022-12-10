package com.gofield.domain.rds.domain.item;

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
@Table(	name = "item_bundle_review")
public class ItemBundleReview  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private ItemBundle bundle;

    @Column
    private Long userId;

    @Column
    private Long orderId;

    @Column
    private String itemNumber;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column(columnDefinition = "TEXT")
    private String optionName;

    @Column
    private String thumbnail;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column
    private Double reviewScore;

    @Column
    private Integer qty;

    @Column
    private String description;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemBundleReviewImage> images = new ArrayList<>();

    @Column
    private LocalDateTime createDate;


    @Builder
    private ItemBundleReview(ItemBundle bundle, Long userId, Long orderId, String itemNumber, String name, String nickName, String optionName, String thumbnail, Integer weight, Integer height, Double reviewScore, int qty, String description){
        this.bundle = bundle;
        this.userId = userId;
        this.orderId = orderId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.nickName = nickName;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.weight = weight;
        this.height = height;
        this.reviewScore = reviewScore;
        this.qty = qty;
        this.description = description;
    }

    public static ItemBundleReview newInstance(ItemBundle bundle, Long userId, Long orderId, String itemNumber, String name, String nickName, String optionName, String thumbnail, Integer weight, Integer height, Double reviewScore, Integer qty, String description){
        return ItemBundleReview.builder()
                .bundle(bundle)
                .userId(userId)
                .orderId(orderId)
                .itemNumber(itemNumber)
                .name(name)
                .nickName(nickName)
                .optionName(optionName)
                .thumbnail(thumbnail)
                .weight(weight)
                .height(height)
                .reviewScore(reviewScore)
                .qty(qty)
                .description(description)
                .build();
    }

    public void addImage(ItemBundleReviewImage image){
        this.images.add(image);
    }
}
