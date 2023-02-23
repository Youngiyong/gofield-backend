package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemAggregationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAggregationRepository extends JpaRepository<ItemAggregation, Long>, ItemAggregationRepositoryCustom {
}
