package com.gofield.domain.rds.entity.review;

import com.gofield.domain.rds.entity.review.repository.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
