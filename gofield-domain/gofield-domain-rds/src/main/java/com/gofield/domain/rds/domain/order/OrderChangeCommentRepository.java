package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderCancelCommentRepositoryCustom;
import com.gofield.domain.rds.domain.order.repository.OrderChangeCommentRepositoryCustom;
import com.gofield.domain.rds.domain.order.repository.OrderChangeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderChangeCommentRepository extends JpaRepository<OrderChangeComment, Long>, OrderChangeCommentRepositoryCustom {
}
