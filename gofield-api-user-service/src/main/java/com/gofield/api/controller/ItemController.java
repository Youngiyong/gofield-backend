package com.gofield.api.controller;

import com.gofield.api.dto.res.ItemBundleResponse;
import com.gofield.api.dto.res.ItemBundleReviewResponse;
import com.gofield.api.dto.res.ItemClassificationResponse;
import com.gofield.api.dto.res.ItemResponse;
import com.gofield.api.service.ItemService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/item")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @ApiOperation(value = "상품 좋아요/해제")
    @PostMapping("/{version}/like/{itemId}")
    public ApiResponse userLikeItem (@PathVariable("version") EApiVersion apiVersion,
                                     @PathVariable Long itemId,
                                     @RequestParam Boolean isLike){
        itemService.userLikeItem(itemId, isLike);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "내가 좋아하는 상품 리스트")
    @GetMapping("/{version}/like")
    public ApiResponse<List<ItemClassificationResponse>> getUserLikeItemList(@PathVariable("version") EApiVersion apiVersion,
                                                                             @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getUserLikeItemList(pageable));
    }

    @ApiOperation(value = "중고 상품 리스트")
    @GetMapping("/{version}/used")
    public ApiResponse<List<ItemClassificationResponse>> getUsedItemList(@PathVariable("version") EApiVersion apiVersion,
                                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                                         @RequestParam(required = false) Long categoryId){
        return ApiResponse.success(itemService.getClassificationItemList(EItemClassificationFlag.USED, categoryId, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 상품 조회")
    @GetMapping("/{version}/bundle/{bundleId}")
    public ApiResponse<ItemBundleResponse> getBundleItemList(@PathVariable("version") EApiVersion apiVersion,
                                                             @PathVariable Long bundleId,
                                                             @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getBundleItemList(bundleId, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 리뷰 조회")
    @GetMapping("/{version}/bundle/{bundleId}/review")
    public ApiResponse<List<ItemBundleReviewResponse>> getBundleItemReviewList(@PathVariable("version") EApiVersion apiVersion,
                                                                               @PathVariable Long bundleId,
                                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getBundleItemReviewList(bundleId, pageable));
    }

    @ApiOperation(value = "상품 상세 조회")
    @GetMapping("/{version}/{itemId}")
    public ApiResponse<ItemResponse> getItem(@PathVariable("version") EApiVersion apiVersion,
                                             @PathVariable Long itemId){
        return ApiResponse.success(itemService.getItem(itemId));
    }
}
