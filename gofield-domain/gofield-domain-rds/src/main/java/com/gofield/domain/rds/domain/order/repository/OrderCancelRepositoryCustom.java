package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderCancel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderCancelRepositoryCustom {
    List<OrderCancel> findAllFetchJoin(Long userId, Pageable pageable);
    OrderCancel findFetchJoin(Long cancelId, Long userId);

}
