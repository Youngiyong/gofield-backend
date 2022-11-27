package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long>, ItemImageRepositoryCustom {
}
