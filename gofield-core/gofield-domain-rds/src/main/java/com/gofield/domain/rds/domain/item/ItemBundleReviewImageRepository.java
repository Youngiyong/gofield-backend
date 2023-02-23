package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemBundleReviewImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleReviewImageRepository extends JpaRepository<ItemBundleReviewImage, Long>, ItemBundleReviewImageRepositoryCustom {
}
