package com.gofield.domain.rds.domain.banner;

import com.gofield.domain.rds.domain.banner.repository.BannerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryCustom {
}
