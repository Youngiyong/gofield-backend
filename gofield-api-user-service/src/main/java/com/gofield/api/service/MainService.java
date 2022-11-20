package com.gofield.api.service;

import com.gofield.api.dto.res.BannerResponse;
import com.gofield.api.dto.res.MainItemResponse;
import com.gofield.domain.rds.entity.banner.Banner;
import com.gofield.domain.rds.entity.banner.BannerRepository;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.item.ItemRepository;
import com.gofield.domain.rds.entity.itemBundle.ItemBundleRepository;
import com.gofield.domain.rds.entity.itemBundleAggregation.ItemBundleAggregationRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.user.UserRepository;
import com.gofield.domain.rds.projections.ItemUsedRecentProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final UserService userService;
    private final BannerRepository bannerRepository;
    private final ItemRepository itemRepository;
    private final ItemBundleRepository itemBundleRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> getBannerList(){
        List<Banner> bannerList = bannerRepository.findAllIsActive();
        return BannerResponse.of(bannerList);
    }

    @Transactional(readOnly = true)
    public MainItemResponse getMainItemList(){
        User user = userService.getUser();
        List<ItemUsedRecentProjection> itemList = itemRepository.findUsedItemRecentList(user.getId());

        return null;
    }
}
