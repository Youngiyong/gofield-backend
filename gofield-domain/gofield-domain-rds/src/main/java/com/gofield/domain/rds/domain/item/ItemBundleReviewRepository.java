package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemBundleReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleReviewRepository extends JpaRepository<ItemBundleReview, Long>, ItemBundleReviewRepositoryCustom {
}
