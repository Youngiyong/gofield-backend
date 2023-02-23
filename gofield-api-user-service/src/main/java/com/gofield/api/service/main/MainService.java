package com.gofield.api.service.main;

import com.gofield.api.service.item.ItemService;
import com.gofield.api.service.item.dto.response.ItemBundlePopularResponse;
import com.gofield.api.service.item.dto.response.ItemBundleRecommendResponse;
import com.gofield.api.service.main.dto.response.BannerListResponse;
import com.gofield.api.service.main.dto.response.MainResponse;
import com.gofield.domain.rds.domain.banner.BannerRepository;
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
    public BannerListResponse retrieveBanners(){
        return BannerListResponse.of( bannerRepository.findAllOrderBySort());
    }

    @Transactional(readOnly = true)
    public MainResponse retrieveItems(){
        List<ItemBundlePopularResponse> popularBundleList = itemService.retrievePopularBundleItems(Pageable.ofSize(30));
        List<ItemBundleRecommendResponse> recommendBundleList = itemService.retrieveRecommendBundleItems(Pageable.ofSize(30));
        return MainResponse.of(popularBundleList, recommendBundleList, recommendBundleList);
    }


}
