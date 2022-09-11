package com.gofield.domain.rds.entity.tag;

import com.gofield.domain.rds.entity.tag.repository.TagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
}
