package com.gofield.api.service;

import com.gofield.api.dto.res.BannerResponse;
import com.gofield.api.dto.res.CategoryMainResponse;
import com.gofield.api.dto.res.MainResponse;
import com.gofield.domain.rds.entity.banner.Banner;
import com.gofield.domain.rds.entity.banner.BannerRepository;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final BannerRepository bannerRepository;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public MainResponse getMainContentList(){
        List<Banner> bannerList = bannerRepository.findAllActive();
        List<Category> categoryList = categoryRepository.findAllIsActiveTrueOrderBySort();
        return MainResponse.of(CategoryMainResponse.of(categoryList), BannerResponse.of(bannerList));
    }


}
