package com.gofield.api.controller;

import com.gofield.api.dto.res.ItemRecentResponse;
import com.gofield.api.service.ItemService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
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

    @ApiOperation(value = "최근 등록된 상품")
    @GetMapping("/{version}/used")
    public ApiResponse<List<ItemRecentResponse>> getRecentItem(@PathVariable("version") EApiVersion apiVersion,
                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @RequestParam Long categoryId){
        return ApiResponse.success(itemService.getRecentItem(categoryId, pageable));
    }
}
