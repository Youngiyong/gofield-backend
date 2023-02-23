package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.TagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
}
