package com.gofield.domain.rds.entity.itemBundleAggregation;

import com.gofield.domain.rds.entity.itemBundleAggregation.repository.ItemBundleAggregationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleAggregationRepository extends JpaRepository<ItemBundleAggregation, Long>, ItemBundleAggregationRepositoryCustom {
}
