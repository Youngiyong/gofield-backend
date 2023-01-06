package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.banner.Banner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@NoArgsConstructor
public class BannerListDto {

    private List<BannerDto> list;
    private Page<Banner> page;


    @Builder
    private BannerListDto(List<BannerDto> list, Page<Banner> page){
        this.list = list;
        this.page = page;
    }

    public static BannerListDto of(List<BannerDto> list, Page<Banner> page){
        return BannerListDto.builder()
                .list(list)
                .page(page)
                .build();
    }

}
