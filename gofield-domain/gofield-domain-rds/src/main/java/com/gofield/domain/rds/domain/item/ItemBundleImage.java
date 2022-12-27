

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
@Table(	name = "item_bundle_image")
public class ItemBundleImage {

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
    private LocalDateTime createDate;

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

}
