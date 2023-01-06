package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.EBannerTypeFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BannerListResponse {

    private List<BannerResponse> topBannerList;
    private List<BannerResponse> middleBannerList;

    @Builder
    public BannerListResponse(List<BannerResponse> topBannerList, List<BannerResponse> middleBannerList){
        this.topBannerList = topBannerList;
        this.middleBannerList = middleBannerList;
    }

    public static BannerListResponse of(List<Banner> list){
        List<BannerResponse> topBannerList = list.stream().sequential().filter(k -> k.getType().equals(EBannerTypeFlag.TOP)).map(p -> BannerResponse.of(p)).collect(Collectors.toList());
        List<BannerResponse> middleBannerList = list.stream().sequential().filter(k -> k.getType().equals(EBannerTypeFlag.MIDDLE)).map(p -> BannerResponse.of(p)).collect(Collectors.toList());
        return BannerListResponse.builder()
                .topBannerList(topBannerList)
                .middleBannerList(middleBannerList)
                .build();
    }
}
