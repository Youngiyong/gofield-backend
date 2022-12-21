package com.gofield.admin.dto;

import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemInfoProjectionResponse;
import com.gofield.domain.rds.domain.item.ItemBundle;
import com.gofield.domain.rds.domain.item.projection.ItemInfoProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemListDto {

    private List<ItemInfoProjectionResponse> list;
    private Page<ItemInfoProjection> page;

    private int allCount;
    private Long salesCount;
    private Long hideCount;
    private Long soldOutCount;

    @Builder
    private ItemListDto(List<ItemInfoProjectionResponse> list, Page<ItemInfoProjection> page,  int allCount, Long salesCount, Long hideCount, Long soldOutCount){
        this.list = list;
        this.page = page;
        this.allCount = allCount;
        this.salesCount = salesCount;
        this.hideCount = hideCount;
        this.soldOutCount = soldOutCount;
    }

    public static ItemListDto of(List<ItemInfoProjectionResponse> list, Page<ItemInfoProjection> page,  int allCount, Long salesCount, Long hideCount, Long soldOutCount){
        return ItemListDto.builder()
                .list(list)
                .page(page)
                .allCount(allCount)
                .salesCount(salesCount)
                .hideCount(hideCount)
                .soldOutCount(soldOutCount)
                .build();
    }

}
