package com.gofield.domain.rds.entity.popularKeyword.repository;

import com.gofield.domain.rds.entity.popularKeyword.PopularKeyword;

import java.util.List;

public interface PopularKeywordRepositoryCustom {
    List<PopularKeyword> findAllPopularKeywordList(int size);
}
