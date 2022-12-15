package com.gofield.admin.dto;

import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.domain.rds.domain.item.ItemBundle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemBundleListDto {

    private List<ItemBundleInfoProjectionResponse> list;
    private Page<ItemBundle> page;

    @Builder
    private ItemBundleListDto(List<ItemBundleInfoProjectionResponse> list, Page<ItemBundle> page){
        this.list = list;
        this.page = page;
    }

    public static ItemBundleListDto of(List<ItemBundleInfoProjectionResponse> list, Page<ItemBundle> page){
        return ItemBundleListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
