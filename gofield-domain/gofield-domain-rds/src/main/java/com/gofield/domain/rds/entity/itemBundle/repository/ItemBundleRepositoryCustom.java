package com.gofield.domain.rds.entity.itemBundle.repository;


import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
import com.gofield.domain.rds.projections.ItemBundleRecommendProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleRepositoryCustom {
    List<ItemBundlePopularProjection> findAllPopularBundleItemList();
    List<ItemBundleRecommendProjection> findAllRecommendBundleItemList();

}
