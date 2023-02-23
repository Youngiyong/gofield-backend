package com.gofield.domain.rds.domain.review;

import com.gofield.domain.rds.domain.review.repository.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
