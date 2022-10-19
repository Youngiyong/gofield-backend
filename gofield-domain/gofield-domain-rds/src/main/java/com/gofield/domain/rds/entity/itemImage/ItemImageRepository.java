package com.gofield.domain.rds.entity.itemImage;

import com.gofield.domain.rds.entity.itemImage.repository.ItemImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long>, ItemImageRepositoryCustom {
}
