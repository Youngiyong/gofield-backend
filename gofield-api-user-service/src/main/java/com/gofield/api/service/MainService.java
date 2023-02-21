package com.gofield.api.service;

import com.gofield.api.dto.res.*;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.BannerRepository;
import com.gofield.domain.rds.domain.banner.EBannerTypeFlag;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final ItemService itemService;
    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public BannerListResponse getBannerListV2(){
        List<Banner> bannerList = bannerRepository.findAllOrderBySort();
        return BannerListResponse.of(bannerList);
    }

    @Transactional(readOnly = true)
    public List<BannerResponse> getBannerList(){
        return BannerResponse.of(bannerRepository.findAllOrderBySort());
    }



    @Transactional(readOnly = true)
    public MainResponse getMainItemList(){
        List<ItemBundlePopularResponse> popularBundleList = itemService.getPopularItemBundleList(Pageable.ofSize(30));
        List<ItemBundleRecommendResponse> recommendBundleList = itemService.getRecommendItemBundleList(Pageable.ofSize(30));
        return MainResponse.of(popularBundleList, recommendBundleList, recommendBundleList);
    }


}
