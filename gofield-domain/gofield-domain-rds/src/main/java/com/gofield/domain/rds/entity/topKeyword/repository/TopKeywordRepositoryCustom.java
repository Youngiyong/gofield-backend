package com.gofield.domain.rds.entity.topKeyword.repository;


import com.gofield.domain.rds.entity.topKeyword.TopKeyword;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopKeywordRepositoryCustom {
    TopKeyword findByKeyword(String keyword);
    List<TopKeyword> findTopKeyword(Pageable pageable);

}
