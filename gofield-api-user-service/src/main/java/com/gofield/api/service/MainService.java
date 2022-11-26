package com.gofield.api.service;

import com.gofield.api.dto.res.*;
import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.banner.BannerRepository;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final ItemService itemService;
    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> getBannerList(){
        List<Banner> bannerList = bannerRepository.findAllIsActive();
        return BannerResponse.of(bannerList);
    }

    @Transactional(readOnly = true)
    public MainResponse getMainItemList(){
        List<ItemBundlePopularResponse> popularBundleList = itemService.getPopularItemBundleList(Pageable.ofSize(30));
        List<ItemBundleRecommendResponse> recommendBundleList = itemService.getRecommendItemBundleList(Pageable.ofSize(30));
        List<ItemClassificationResponse> classificationItemList = itemService.getClassificationItemList(EItemClassificationFlag.USED, null, Pageable.ofSize(30));
        return MainResponse.of(popularBundleList, recommendBundleList, recommendBundleList, classificationItemList);
    }


}
