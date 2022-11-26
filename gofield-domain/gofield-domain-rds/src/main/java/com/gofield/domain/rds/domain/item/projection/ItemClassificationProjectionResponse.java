package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemClassificationProjectionResponse {

    private Long id;
    private String itemNumber;
    private String name;
    private String brandName;
    private String thumbnail;
    private int price;
    private Long likeId;
    private EItemClassificationFlag classification;
    private EItemGenderFlag gender;
    private String tags;

    @Builder
    public ItemClassificationProjectionResponse(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, String tags) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.likeId = likeId;
        this.classification = classification;
        this.gender = gender;
        this.tags = tags;
    }

    public static ItemClassificationProjectionResponse of(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, String tags){
        return ItemClassificationProjectionResponse.builder()
                .id(id)
                .itemNumber(itemNumber)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .price(price)
                .likeId(likeId)
                .classification(classification)
                .gender(gender)
                .tags(tags)
                .build();
    }

    public static List<ItemClassificationProjectionResponse> of(List<ItemClassificationProjection> list){
        return list
                .stream()
                .map(p -> ItemClassificationProjectionResponse.of(p.getId(), p.getItemNumber(), p.getName(),
                        p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getLikeId(), p.getClassification(), p.getGender(), p.getTags()))
                .collect(Collectors.toList());
    }

    public static List<ItemClassificationProjectionResponse> ofNon(List<ItemNonMemberClassificationProjection> list){
        return list
                .stream()
                .map(p -> ItemClassificationProjectionResponse.of(p.getId(), p.getItemNumber(), p.getName(),
                        p.getBrandName(), p.getThumbnail(), p.getPrice(), null, p.getClassification(), p.getGender(), p.getTags()))
                .collect(Collectors.toList());
    }

    public static ItemClassificationProjectionResponse of(ItemNonMemberClassificationProjection projection){
        return ItemClassificationProjectionResponse.builder()
                .id(projection.getId())
                .itemNumber(projection.getItemNumber())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .price(projection.getPrice())
                .likeId(null)
                .classification(projection.getClassification())
                .gender(projection.getGender())
                .tags(projection.getTags())
                .build();
    }
}
