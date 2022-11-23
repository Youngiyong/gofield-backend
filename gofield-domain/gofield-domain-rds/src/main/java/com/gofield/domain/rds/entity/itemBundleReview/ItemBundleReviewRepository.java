package com.gofield.domain.rds.entity.itemBundleReview;

import com.gofield.domain.rds.entity.itemBundleReview.repository.ItemBundleReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleReviewRepository extends JpaRepository<ItemBundleReview, Long>, ItemBundleReviewRepositoryCustom {
}
