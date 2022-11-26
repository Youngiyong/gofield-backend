package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemBundleAggregationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleAggregationRepository extends JpaRepository<ItemBundleAggregation, Long>, ItemBundleAggregationRepositoryCustom {
}
