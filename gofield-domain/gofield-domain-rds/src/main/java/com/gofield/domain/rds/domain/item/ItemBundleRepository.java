package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemBundleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleRepository extends JpaRepository<ItemBundle, Long>, ItemBundleRepositoryCustom {
}
