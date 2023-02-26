package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.OrderCancel;
import com.gofield.domain.rds.domain.order.projection.OrderCancelInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderChangeInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderReturnInfoAdminProjectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderCancelRepositoryCustom {
    Page<OrderCancel> findAllFetchJoin(Long userId, Pageable pageable);
    OrderCancel findByCancelIdAndUserIdFetchJoin(Long cancelId, Long userId);

    OrderCancel findByCancelIdFetchJoin(Long cancelId);

    OrderCancel findByShippingIdAndStatusReturn(Long orderShippingId);

    List<OrderCancel> findAllOrderCancelByKeyword(String keyword, EOrderCancelStatusFlag status);
    List<OrderCancel> findAllOrderChangeByKeyword(String keyword, EOrderCancelStatusFlag status);
    List<OrderCancel> findAllOrderReturnByKeyword(String keyword, EOrderCancelStatusFlag status);

    OrderCancelInfoAdminProjectionResponse findAllOrderCancelByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable);
    OrderChangeInfoAdminProjectionResponse findAllOrderChangeByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable);
    OrderReturnInfoAdminProjectionResponse findAllOrderReturnByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable);
}
