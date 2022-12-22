package com.gofield.admin.service;


import com.gofield.admin.dto.OrderCancelDto;
import com.gofield.admin.dto.OrderChangeDto;
import com.gofield.admin.dto.OrderReturnDto;
import com.gofield.admin.dto.OrderShippingDto;
import com.gofield.admin.dto.response.projection.AdminInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.BrandInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemBundleInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.ItemInfoProjectionResponse;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ItemService itemService;
    private final BrandService brandService;
    private final ItemBundleService itemBundleService;

    private final OrderService orderService;

    public List<BrandInfoProjectionResponse> downloadBrands(String keyword){
        return brandService.downloadBrands(keyword);
    }

    public List<ItemBundleInfoProjectionResponse> downloadBundles(String keyword){
        return itemBundleService.downloadBundles(keyword);
    }

    public List<ItemInfoProjectionResponse> downloadItems(String keyword, EItemStatusFlag status){
        return itemService.downloadItems(keyword, status);
    }

    public List<OrderShippingDto> downloadOrderShipping(String keyword, EOrderShippingStatusFlag status){
        return orderService.downloadOrderShipping(keyword, status);
    }

    public List<OrderCancelDto> downloadOrderCancels(String keyword, EOrderCancelStatusFlag status){
        return orderService.downloadOrderCancels(keyword, status);
    }

    public List<OrderChangeDto> downloadOrderChanges(String keyword, EOrderCancelStatusFlag status){
        return orderService.downloadOrderChanges(keyword, status);
    }

    public List<OrderReturnDto> downloadOrderReturns(String keyword, EOrderCancelStatusFlag status){
        return orderService.downloadOrderReturns(keyword, status);
    }
}
