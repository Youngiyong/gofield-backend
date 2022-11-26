package com.gofield.domain.rds.domain.banner.repository;

import com.gofield.domain.rds.domain.banner.Banner;

import java.util.List;

public interface BannerRepositoryCustom {

    List<Banner> findAllIsActive();
}
