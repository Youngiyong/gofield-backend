package com.gofield.domain.rds.entity.itemAggregation;

import com.gofield.domain.rds.entity.itemAggregation.repository.ItemAggregationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAggregationRepository extends JpaRepository<ItemAggregation, Long>, ItemAggregationRepositoryCustom {
}
