package com.gofield.domain.rds.domain.item.projection;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
public class ItemInfoAdminProjectionResponse {

    private Page<ItemInfoProjection> page;
    private int allCount;
    private Long salesCount;
    private Long hideCount;
    private Long soldOutCount;

    @Builder
    private ItemInfoAdminProjectionResponse(Page<ItemInfoProjection> page, int allCount, Long salesCount, Long hideCount, Long soldOutCount){
        this.page = page;
        this.allCount = allCount;
        this.salesCount = salesCount;
        this.hideCount = hideCount;
        this.soldOutCount = soldOutCount;
    }

    public static ItemInfoAdminProjectionResponse of(Page<ItemInfoProjection> page, int allCount, Long salesCount, Long hideCount, Long soldOutCount){
        return ItemInfoAdminProjectionResponse.builder()
                .page(page)
                .allCount(allCount)
                .salesCount(salesCount)
                .hideCount(hideCount)
                .soldOutCount(soldOutCount)
                .build();
    }
}
