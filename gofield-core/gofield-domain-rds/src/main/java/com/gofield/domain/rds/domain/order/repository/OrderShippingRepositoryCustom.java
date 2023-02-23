package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderShipping;
import com.gofield.domain.rds.domain.order.projection.OrderShippingInfoAdminProjectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderShippingRepositoryCustom {
    OrderShipping findByShippingNumberAndOrderNumberFetchOrder(Long userId, String shippingNumber, String orderNumber);
    OrderShipping findByShippingNumberAndOrderNumberFetch(Long userId, String shippingNumber, String orderNumber);;
    OrderShippingInfoAdminProjectionResponse findAllOrderShippingByKeyword(String keyword, EOrderShippingStatusFlag status, Pageable pageable);
    List<OrderShipping> findAllByKeyword(String keyword, EOrderShippingStatusFlag status);
    OrderShipping findAdminOrderShippingById(Long id);
    OrderShipping findByIdFetchOrder(Long id);
    OrderShipping findByIdNotFetch(Long id);

    OrderShipping findByUserIdAndShippingNumberFetch(Long userId, String shippingNumber);
}
