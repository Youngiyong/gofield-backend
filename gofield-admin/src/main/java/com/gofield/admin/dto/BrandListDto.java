package com.gofield.admin.dto;

import com.gofield.admin.dto.response.projection.BrandInfoProjectionResponse;
import com.gofield.domain.rds.domain.item.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class BrandListDto {

    private List<BrandInfoProjectionResponse> list;
    private Page<Brand> page;


    @Builder
    private BrandListDto(List<BrandInfoProjectionResponse> list, Page<Brand> page){
        this.list = list;
        this.page = page;
    }

    public static BrandListDto of(List<BrandInfoProjectionResponse> list, Page<Brand> page){
        return BrandListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
