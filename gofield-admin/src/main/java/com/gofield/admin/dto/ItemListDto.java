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

    @Builder
    private ItemListDto(List<ItemInfoProjectionResponse> list, Page<ItemInfoProjection> page){
        this.list = list;
        this.page = page;
    }

    public static ItemListDto of(List<ItemInfoProjectionResponse> list, Page<ItemInfoProjection> page){
        return ItemListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
