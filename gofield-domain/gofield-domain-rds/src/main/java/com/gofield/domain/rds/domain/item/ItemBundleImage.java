

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

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_bundle_image")
public class ItemBundleImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    private ItemBundle bundle;

    @Column(name = "image_path")
    private String image;

    @Column
    private Integer sort;

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private ItemBundleImage(ItemBundle bundle, String image, Integer sort){
        this.bundle = bundle;
        this.image = image;
        this.sort = sort;
    }

    public static ItemBundleImage newInstance(ItemBundle bundle, String image, Integer sort){
        return ItemBundleImage.builder()
                .bundle(bundle)
                .image(image)
                .sort(sort)
                .build();
    }

    public void updateDeleteDate(){
        this.deleteDate = LocalDateTime.now();
    }
}
