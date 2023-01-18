package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private int deliveryPrice;
    private Long likeId;
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private String tags;
    private LocalDateTime createDate;

    @Builder
    public ItemClassificationProjectionResponse(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, int deliveryPrice,  Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, LocalDateTime createDate) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.deliveryPrice =deliveryPrice;
        this.likeId = likeId;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.tags = tags;
        this.createDate = createDate;
    }

    public static ItemClassificationProjectionResponse of(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, int deliveryPrice, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, LocalDateTime createDate){
        return ItemClassificationProjectionResponse.builder()
                .id(id)
                .itemNumber(itemNumber)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .likeId(likeId)
                .classification(classification)
                .spec(spec)
                .delivery(delivery)
                .gender(gender)
                .tags(tags)
                .createDate(createDate)
                .build();
    }

    public static List<ItemClassificationProjectionResponse> of(List<ItemClassificationProjection> list){
        return list
                .stream()
                .map(p -> ItemClassificationProjectionResponse.of(p.getId(), p.getItemNumber(), p.getName(),
                        p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getDeliveryPrice(), p.getLikeId(), p.getClassification(), p.getSpec(), p.getDelivery(), p.getGender(), p.getTags(), p.getCreateDate()))
                .collect(Collectors.toList());
    }

    public static List<ItemClassificationProjectionResponse> ofNon(List<ItemNonMemberClassificationProjection> list){
        return list
                .stream()
                .map(p -> ItemClassificationProjectionResponse.of(p.getId(), p.getItemNumber(), p.getName(),
                        p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getDeliveryPrice(), null, p.getClassification(), p.getSpec(), p.getDelivery(),  p.getGender(), p.getTags(), p.getCreateDate()))
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
                .deliveryPrice(projection.getDeliveryPrice())
                .likeId(null)
                .classification(projection.getClassification())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .tags(projection.getTags())
                .createDate(projection.getCreateDate())
                .build();
    }
}
