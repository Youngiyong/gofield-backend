package com.gofield.api.controller;

import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.*;
import com.gofield.api.service.ItemService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation(value ="인기 상품 리스트")
    @GetMapping("/{version}/popular")
    public ApiResponse<List<ItemBundlePopularResponse>> getPopularItemBundleList(@PathVariable("version") EApiVersion apiVersion,
                                                                                 @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getPopularItemBundleList(pageable));
    }

    @ApiOperation(value ="추천 상품 리스트")
    @GetMapping("/{version}/recommend")
    public ApiResponse<List<ItemBundleRecommendResponse>> getRecommendItemBundleList(@PathVariable("version") EApiVersion apiVersion,
                                                                                     @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getRecommendItemBundleList(pageable));
    }

    @ApiOperation(value ="종목 추천 상품 리스트")
    @GetMapping("/{version}/recommend/category")
    public ApiResponse<List<ItemBundleRecommendResponse>> getRecommendCategoryItemBundleList(@PathVariable("version") EApiVersion apiVersion,
                                                                                             @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getRecommendItemBundleList(pageable));
    }

    @ApiOperation(value = "새상품/중고 상품 리스트")
    @GetMapping("/{version}")
    public ApiResponse<List<ItemClassificationResponse>> getItemList(@PathVariable("version") EApiVersion apiVersion,
                                                                     @RequestParam(required = false) Long categoryId,
                                                                     @RequestParam(required = false) EItemClassificationFlag classification,
                                                                     @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getClassificationItemList(classification, categoryId, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 카테고리 조회")
    @GetMapping("/{version}/bundle")
    public ApiResponse<List<ItemBundleCategoryResponse>> getBundleCategoryList(@PathVariable("version") EApiVersion apiVersion,
                                                                               @RequestParam Long categoryId,
                                                                               @RequestParam(required = false) Long subCategoryId,
                                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getCategoryItemBundleList(categoryId, subCategoryId, pageable));
    }


    @ApiOperation(value = "묶음 상품 - 상품 조회")
    @GetMapping("/{version}/bundle/{bundleId}")
    public ApiResponse<ItemBundleResponse> getBundleItemList(@PathVariable("version") EApiVersion apiVersion,
                                                             @PathVariable Long bundleId,
                                                             @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getBundleItemList(bundleId, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 다른 (새/중고) 상품 조회")
    @GetMapping("/{version}/bundle/{bundleId}/other/{itemId}")
    public ApiResponse<List<ItemClassificationResponse>> getBundleOtherItemList(@PathVariable("version") EApiVersion apiVersion,
                                                                                @PathVariable Long bundleId,
                                                                                @PathVariable Long itemId,
                                                                                @RequestParam(required = false) EItemClassificationFlag classification,
                                                                                @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getOtherItemList(bundleId, itemId, classification, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 리뷰 조회")
    @GetMapping("/{version}/bundle/{bundleId}/review")
    public ApiResponse<List<ItemBundleReviewResponse>> getBundleItemReviewList(@PathVariable("version") EApiVersion apiVersion,
                                                                               @PathVariable Long bundleId,
                                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getBundleItemReviewList(bundleId, pageable));
    }

    @ApiOperation(value = "상품 상세 조회")
    @GetMapping("/{version}/{itemNumber}")
    public ApiResponse<ItemResponse> getItem(@PathVariable("version") EApiVersion apiVersion,
                                             @PathVariable String itemNumber){
        return ApiResponse.success(itemService.getItem(itemNumber));
    }

    @ApiOperation(value = "상품 옵션 조회")
    @GetMapping("/{version}/{itemId}/option")
    public ApiResponse<ItemOptionDetailResponse> getItemOption(@PathVariable("version") EApiVersion apiVersion,
                                                               @PathVariable Long itemId){
        return ApiResponse.success(itemService.getItemOption(itemId));
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 리스트")
    @GetMapping("/{version}/{itemId}/qna")
    public ApiResponse<List<ItemQnaResponse>> getQnaList(@PathVariable("version") EApiVersion apiVersion,
                                                         @PathVariable Long itemId,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean isMe,
                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getQnaList(itemId, isMe, pageable));
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 상세")
    @GetMapping("/{version}/{itemId}/qna/{qnaId}")
    public ApiResponse<ItemQnaDetailResponse> getQna(@PathVariable("version") EApiVersion apiVersion,
                                                     @PathVariable Long itemId,
                                                     @PathVariable Long qnaId){
        return ApiResponse.success(itemService.getQna(itemId, qnaId));
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 등록")
    @PostMapping("/{version}/{itemId}/qna")
    public ApiResponse insertQna(@PathVariable("version") EApiVersion apiVersion,
                                 @PathVariable Long itemId,
                                 @Valid @RequestBody ItemRequest.ItemQna request){
        itemService.insertQna(itemId, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 삭제")
    @DeleteMapping("/{version}/{itemId}}/qna/{qnaId}")
    public ApiResponse deleteQna(@PathVariable("version") EApiVersion apiVersion,
                                 @PathVariable Long itemId,
                                 @PathVariable Long qnaId){
        itemService.deleteQna(itemId, qnaId);
        return ApiResponse.SUCCESS;
    }
}
