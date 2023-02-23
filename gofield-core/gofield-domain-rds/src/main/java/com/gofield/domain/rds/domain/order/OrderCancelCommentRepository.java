package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderCancelCommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelCommentRepository extends JpaRepository<OrderCancelComment, Long>, OrderCancelCommentRepositoryCustom {
}
