package com.gofield.api.controller;

import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.*;
import com.gofield.api.service.ItemService;
import com.gofield.common.model.dto.res.ApiResponse;
import com.gofield.domain.rds.domain.item.EItemBundleSort;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemSort;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
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
    @PostMapping("/v1/like/{itemId}")
    public ApiResponse userLikeItem (@PathVariable Long itemId,
                                     @Valid @RequestBody ItemRequest.ItemLike request){
        itemService.userLikeItem(itemId, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "내가 좋아하는 상품 리스트")
    @GetMapping("/v1/like")
    public ApiResponse<List<ItemClassificationResponse>> getUserLikeItemList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getUserLikeItemList(pageable));
    }

    @ApiOperation(value = "최근 본 상품 리스트")
    @GetMapping("/v1/recent")
    public ApiResponse<List<ItemClassificationResponse>> getUserRecentItemList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getUserRecentItemList(pageable));
    }

    @ApiOperation(value = "인기 상품 리스트")
    @GetMapping("/v1/popular")
    public ApiResponse<List<ItemBundlePopularResponse>> getPopularItemBundleList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getPopularItemBundleList(pageable));
    }

    @ApiOperation(value = "추천 상품 리스트")
    @GetMapping("/v1/recommend")
    public ApiResponse<List<ItemBundleRecommendResponse>> getRecommendItemBundleList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getRecommendItemBundleList(pageable));
    }

    @ApiOperation(value ="종목 추천 상품 리스트")
    @GetMapping("/v1/recommend/category")
    public ApiResponse<List<ItemBundleRecommendResponse>> getRecommendCategoryItemBundleList(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getRecommendItemBundleList(pageable));
    }

    @ApiOperation(value = "새상품/중고 상품 리스트")
    @GetMapping("/v1")
    public ApiResponse<List<ItemClassificationResponse>> getItemList(@RequestParam(required = false) EItemClassificationFlag classification,
                                                                     @RequestParam(required = false) Long categoryId,
                                                                     @RequestParam(required = false) List<EItemSpecFlag> spec,
                                                                     @RequestParam(required = false) List<EItemSort> sort,
                                                                     @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getClassificationItemList(classification, categoryId, spec,  sort, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 카테고리 조회")
    @GetMapping("/v1/bundle")
    public ApiResponse<List<ItemBundleCategoryResponse>> getBundleCategoryList(@RequestParam Long categoryId,
                                                                               @RequestParam(required = false) Long subCategoryId,
                                                                               @RequestParam(required = false) EItemBundleSort sort,
                                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getCategoryItemBundleList(categoryId, subCategoryId, sort, pageable));
    }

    @ApiOperation(value = "묶음 상품 상세 조회")
    @GetMapping("/v1/bundle/{bundleId}")
    public ApiResponse<ItemBundleResponse> getBundleItemList(@PathVariable Long bundleId){
        return ApiResponse.success(itemService.getBundle(bundleId));
    }

    @ApiOperation(value = "묶음 상품 - 상품 리스트 조회")
    @GetMapping("/v1/bundle/{bundleId}/item")
    public ApiResponse<ItemClassificationPaginationResponse> getBundleItemList(@PathVariable Long bundleId,
                                                                               @RequestParam(required = false) EItemClassificationFlag classification,
                                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getBundleItemList(bundleId, classification, pageable));
    }


    @ApiOperation(value = "묶음 상품 - 다른 (새/중고) 상품 조회")
    @GetMapping("/v1/bundle/{bundleId}/other/{itemId}")
    public ApiResponse<List<ItemClassificationResponse>> getBundleOtherItemList(@PathVariable Long bundleId,
                                                                                @PathVariable Long itemId,
                                                                                @RequestParam(required = false) EItemClassificationFlag classification,
                                                                                @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.getOtherItemList(bundleId, itemId, classification, pageable));
    }

    @ApiOperation(value = "묶음 상품 - 리뷰 조회")
    @GetMapping("/v1/bundle/{bundleId}/review")
    public ApiResponse<ItemBundleReviewListResponse> getBundleItemReviewList(@PathVariable Long bundleId,
                                                                             @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(itemService.getBundleItemReviewList(bundleId, pageable));
    }

    @ApiOperation(value = "상품 상세 조회")
    @GetMapping("/v1/{itemNumber}")
    public ApiResponse<ItemResponse> getItem(@PathVariable String itemNumber){
        return ApiResponse.success(itemService.getItem(itemNumber));
    }

    @ApiOperation(value = "상품 옵션 조회")
    @GetMapping("/v1/{itemId}/option")
    public ApiResponse<ItemOptionDetailResponse> getItemOption(@PathVariable Long itemId){
        return ApiResponse.success(itemService.getItemOption(itemId));
    }

    @ApiOperation(value = "상품 상세 - 판매자 정보 조회")
    @GetMapping("/v1/{itemNumber}/seller")
    public ApiResponse<SellerShippingResponse> getItemShippingTemplate(@PathVariable String itemNumber){
        return ApiResponse.success(itemService.getItemSeller(itemNumber));
    }


    @ApiOperation(value = "상품 상세 - 상품 문의 리스트")
    @GetMapping("/v1/{itemId}/qna")
    public ApiResponse<ItemQnaPaginationResponse> getQnaList(@PathVariable Long itemId,
                                                             @RequestParam(required = false, defaultValue = "false") Boolean isMe,
                                                             @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(itemService.getQnaList(itemId, isMe, pageable));
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 상세")
    @GetMapping("/v1/{itemId}/qna/{qnaId}")
    public ApiResponse<ItemQnaDetailResponse> getQna(@PathVariable Long itemId,
                                                     @PathVariable Long qnaId){
        return ApiResponse.success(itemService.getQna(itemId, qnaId));
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 등록")
    @PostMapping("/v1/{itemId}/qna")
    public ApiResponse createItemQna(@PathVariable Long itemId,
                                     @Valid @RequestBody ItemRequest.ItemQna request){
        itemService.createItemQna(itemId, request);
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "상품 상세 - 상품 문의 삭제")
    @DeleteMapping("/v1/{itemId}}/qna/{qnaId}")
    public ApiResponse deleteQna(@PathVariable Long itemId,
                                 @PathVariable Long qnaId){
        itemService.deleteQna(itemId, qnaId);
        return ApiResponse.SUCCESS;
    }
}
