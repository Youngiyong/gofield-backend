package com.gofield.api.controller;

import com.gofield.api.service.ItemService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
}
