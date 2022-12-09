
package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.user.User;
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
    private String name;

    @Column
    private String nickName;

    @Column(columnDefinition = "TEXT")
    private String optionName;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column
    private Double reviewScore;

    @Column
    private String description;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemBundleReviewImage> images = new ArrayList<>();

    @Column
    private LocalDateTime createDate;


    @Builder
    private ItemBundleReview(ItemBundle bundle, Long userId, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScore, String description){
        this.bundle = bundle;
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.optionName = optionName;
        this.weight = weight;
        this.height = height;
        this.reviewScore = reviewScore;
        this.description = description;
    }

    public static ItemBundleReview newInstance(ItemBundle bundle, Long userId, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScore, String description){
        return ItemBundleReview.builder()
                .bundle(bundle)
                .userId(userId)
                .name(name)
                .nickName(nickName)
                .optionName(optionName)
                .weight(weight)
                .height(height)
                .reviewScore(reviewScore)
                .description(description)
                .build();
    }

    public void addImage(ItemBundleReviewImage image){
        this.images.add(image);
    }
}
