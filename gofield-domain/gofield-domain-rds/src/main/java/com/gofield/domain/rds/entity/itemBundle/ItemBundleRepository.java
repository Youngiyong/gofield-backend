package com.gofield.domain.rds.entity.itemBundle;

import com.gofield.domain.rds.entity.itemBundle.repository.ItemBundleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBundleRepository extends JpaRepository<ItemBundle, Long>, ItemBundleRepositoryCustom {
}
