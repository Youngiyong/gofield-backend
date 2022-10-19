package com.gofield.domain.rds.entity.itemBundleImage;

import com.gofield.domain.rds.entity.itemBundleImage.repository.ItemBundleImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleImageRepository extends JpaRepository<ItemBundleImage, Long>, ItemBundleImageRepositoryCustom {
}
