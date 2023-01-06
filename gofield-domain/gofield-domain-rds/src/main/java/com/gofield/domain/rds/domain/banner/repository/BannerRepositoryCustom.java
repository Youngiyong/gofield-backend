package com.gofield.domain.rds.domain.banner.repository;

import com.gofield.domain.rds.domain.banner.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerRepositoryCustom {

    List<Banner> findAllOrderBySort();
    Page<Banner> findAllPaging(Pageable pageable);
    Banner findByBannerId(Long bannerId);
}
