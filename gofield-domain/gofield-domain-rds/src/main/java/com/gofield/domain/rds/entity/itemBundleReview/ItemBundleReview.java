
package com.gofield.domain.rds.entity.itemBundleReview;

import com.gofield.domain.rds.entity.itemBundle.ItemBundle;
import com.gofield.domain.rds.entity.itemBundleReviewImage.ItemBundleReviewImage;
import com.gofield.domain.rds.entity.user.User;
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
@Table(	name = "item_bundle_review")
public class ItemBundleReview  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private ItemBundle bundle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column
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

    public void addImage(ItemBundleReviewImage image){
        this.images.add(image);
    }
}
