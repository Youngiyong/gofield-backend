package com.gofield.domain.rds.domain.search.repository;

import com.gofield.domain.rds.domain.search.PopularKeyword;

import java.util.List;

public interface PopularKeywordRepositoryCustom {
    List<PopularKeyword> findAllPopularKeywordList(int size);
}
