package com.gofield.domain.rds.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long>, ItemImageRepositoryCustom {
}
