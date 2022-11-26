package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemBundleImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleImageRepository extends JpaRepository<ItemBundleImage, Long>, ItemBundleImageRepositoryCustom {
}
