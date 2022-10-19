package com.gofield.domain.rds.entity.banner.repository;

import com.gofield.domain.rds.entity.banner.Banner;

import java.util.List;

public interface BannerRepositoryCustom {

    List<Banner> findAllIsActive();
}
