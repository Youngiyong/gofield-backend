package com.gofield.api.controller;

import com.gofield.api.dto.res.ItemClassificationResponse;
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

    @ApiOperation(value = "상품 좋아요")
    @PostMapping("/{version}/{itemId}/like")
    public ApiResponse userLikeItem (@PathVariable("version") EApiVersion apiVersion,
                                     @PathVariable Long itemId,
                                     @RequestParam Boolean isLike){
        itemService.userLikeItem(itemId, isLike);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "인기 상품 리스트")
    @GetMapping("/{version}/popular")
    public ApiResponse getPopularItemBundleList(@PathVariable("version") EApiVersion apiVersion,
                                                @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){


        return ApiResponse.success(itemService.getPopularItemBundleList());
    }

    @ApiOperation(value = "최근 등록된 상품 리스트")
    @GetMapping("/{version}/recent")
    public ApiResponse<List<ItemClassificationResponse>> getRecentItemList(@PathVariable("version") EApiVersion apiVersion,
                                                                           @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                                           @RequestParam Long categoryId){
        return ApiResponse.success(itemService.getClassificationItemList(null, categoryId, pageable));
    }

    @ApiOperation(value = "중고 상품 리스트")
    @GetMapping("/{version}/used")
    public ApiResponse<List<ItemClassificationResponse>> getUsedItemList(@PathVariable("version") EApiVersion apiVersion,
                                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                                         @RequestParam(required = false) Long categoryId){
        return ApiResponse.success(itemService.getClassificationItemList(EItemClassificationFlag.USED, categoryId, pageable));
    }

}
