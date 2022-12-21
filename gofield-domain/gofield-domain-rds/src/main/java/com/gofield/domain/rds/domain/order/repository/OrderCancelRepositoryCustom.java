package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderCancel;
import com.gofield.domain.rds.domain.order.projection.OrderCancelInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderShippingInfoAdminProjectionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderCancelRepositoryCustom {
    List<OrderCancel> findAllFetchJoin(Long userId, Pageable pageable);
    OrderCancel findFetchJoin(Long cancelId, Long userId);

    OrderCancelInfoAdminProjectionResponse findAllOrderCancelByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable);

}
