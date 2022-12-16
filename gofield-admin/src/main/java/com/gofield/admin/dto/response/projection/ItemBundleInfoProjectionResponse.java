package com.gofield.admin.dto.response.projection;

import com.gofield.domain.rds.domain.item.ItemBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundleInfoProjectionResponse {
    private Long id;
    private String categoryName;
    private String brandName;
    private String name;
    private String createDate;


    @Builder
    private ItemBundleInfoProjectionResponse(Long id, String categoryName, String brandName, String name, String createDate){
        this.id = id;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.name = name;
        this.createDate = createDate;
    }

    public static ItemBundleInfoProjectionResponse of(ItemBundle itemBundle){
        return ItemBundleInfoProjectionResponse.builder()
                .id(itemBundle.getId())
                .categoryName(itemBundle.getCategory().getName())
                .brandName(itemBundle.getBrand().getName())
                .name(itemBundle.getName())
                .createDate(itemBundle.getCreateDate().toLocalDate().toString())
                .build();
    }

    public static List<ItemBundleInfoProjectionResponse> of(List<ItemBundle> list){
        return list.stream()
                .map(p -> ItemBundleInfoProjectionResponse.of(p))
                .collect(Collectors.toList());
    }
}
