package com.gofield.domain.rds.entity.itemBundleReviewImage;

import com.gofield.domain.rds.entity.itemBundleReviewImage.repository.ItemBundleReviewImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleReviewImageRepository extends JpaRepository<ItemBundleReviewImage, Long>, ItemBundleReviewImageRepositoryCustom {
}
