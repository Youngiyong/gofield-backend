package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.order.projection.OrderCancelInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderShippingInfoAdminProjectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CodeRepository codeRepository;

    private final OrderCancelRepository orderCancelRepository;

    private final OrderShippingRepository orderShippingRepository;

    @Transactional(readOnly = true)
    public OrderShippingListDto getOrderList(String keyword, EOrderShippingStatusFlag status, Pageable pageable){
        OrderShippingInfoAdminProjectionResponse page = orderShippingRepository.findAllOrderShippingByKeyword(keyword, status, pageable);
        List<CodeDto> codeList = CodeDto.of(codeRepository.findAllByGroupByIsHide(ECodeGroup.CARRIER, false));
        List<OrderShippingDto> result = OrderShippingDto.of(page.getPage().getContent(), codeList);
        return OrderShippingListDto.of(result, page.getPage(), page.getAllCount(), page.getReceiptCount(), page.getReadyCount(), page.getDeliveryCount(), page.getDeliveryCompleteCount());
    }

    @Transactional(readOnly = true)
    public OrderCancelListDto getOrderCancelList(String keyword, EOrderCancelStatusFlag status, Pageable pageable){
        OrderCancelInfoAdminProjectionResponse page = orderCancelRepository.findAllOrderCancelByKeyword(keyword, status, pageable);
        List<OrderCancelDto> result = OrderCancelDto.of(page.getPage().getContent());
        return OrderCancelListDto.of(result, page.getPage(), page.getAllCount(), page.getReceiptCount(), page.getRefuseCount(), page.getCompleteCount());
    }

    @Transactional(readOnly = true)
    public List<OrderShippingDto> downloadOrderShipping(String keyword, EOrderShippingStatusFlag status){
        return OrderShippingDto.of(orderShippingRepository.findAllByKeyword(keyword, status), null);
    }

    @Transactional(readOnly = true)
    public OrderShippingDto getOrderShipping(Long id){
        OrderShipping orderShipping = orderShippingRepository.findAdminOrderShippingById(id);
        List<CodeDto> codeList = CodeDto.of(codeRepository.findAllByGroupByIsHide(ECodeGroup.CARRIER, false));
        return OrderShippingDto.of(orderShipping, codeList);
    }

    @Transactional
    public void updateOrderShipping(OrderShippingDto request){
        OrderShipping orderShipping = orderShippingRepository.findByIdNotFetch(request.getId());
        orderShipping.updateCarrier(request.getCarrier(), request.getTrackingNumber());
    }
}
