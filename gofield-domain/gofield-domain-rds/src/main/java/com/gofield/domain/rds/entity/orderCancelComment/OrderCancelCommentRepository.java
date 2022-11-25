package com.gofield.domain.rds.entity.orderCancelComment;

import com.gofield.domain.rds.entity.orderCancelComment.repository.OrderCancelCommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelCommentRepository extends JpaRepository<OrderCancelComment, Long>, OrderCancelCommentRepositoryCustom {
}
