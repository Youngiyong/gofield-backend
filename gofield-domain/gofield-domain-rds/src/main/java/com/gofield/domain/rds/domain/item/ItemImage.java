package com.gofield.domain.rds.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "item_image")
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "image_path")
    private String image;

    @Column
    private LocalDateTime createDate;

    @Builder
    private ItemImage(Item item, String image){
        this.item = item;
        this.image= image;
        this.createDate = LocalDateTime.now();
    }

    public static ItemImage of(Item item, String image){
        return ItemImage.builder()
                .item(item)
                .image(image)
                .build();
    }
}
