

package com.gofield.domain.rds.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_bundle_review_image")
public class ItemBundleReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ItemBundleReview review;

    @Column(name = "image_path")
    private String image;

    @Column
    private LocalDateTime createDate;

    @Builder
    private ItemBundleReviewImage(ItemBundleReview review, String image){
        this.review = review;
        this.image = image;
    }

    public static ItemBundleReviewImage newInstance(ItemBundleReview review, String image){
        return new ItemBundleReviewImage(review, image);
    }

}
