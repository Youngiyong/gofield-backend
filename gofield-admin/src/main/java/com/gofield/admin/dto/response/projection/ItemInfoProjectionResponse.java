package com.gofield.admin.dto.response.projection;

import com.gofield.domain.rds.domain.item.projection.ItemInfoProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemInfoProjectionResponse {
    private Long id;
    private String name;
    private String classification;
    private int price;
    private String categoryName;
    private String status;
    private String createDate;

    @Builder
    private ItemInfoProjectionResponse(Long id, String name, String classification, int price, String categoryName, String status, String createDate){
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.price = price;
        this.categoryName = categoryName;
        this.status = status;
        this.createDate = createDate;
    }

    public static ItemInfoProjectionResponse of(ItemInfoProjection itemInfoProjection){
        return ItemInfoProjectionResponse.builder()
                .id(itemInfoProjection.getId())
                .name(itemInfoProjection.getName())
                .classification(itemInfoProjection.getClassification().getDescription())
                .price(itemInfoProjection.getPrice())
                .categoryName(itemInfoProjection.getCategoryName())
                .status(itemInfoProjection.getStatus().getDescription())
                .createDate(itemInfoProjection.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<ItemInfoProjectionResponse> of(List<ItemInfoProjection> list){
        return list.stream()
                .map(p -> ItemInfoProjectionResponse.of(p))
                .collect(Collectors.toList());
    }
}
