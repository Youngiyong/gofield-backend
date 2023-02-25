package com.gofield.api.controller.item;

import com.gofield.api.config.interceptor.Auth;
import com.gofield.api.config.resolver.UserIdResolver;
import com.gofield.api.service.item.dto.request.ItemRequest;
import com.gofield.api.service.item.ItemService;
import com.gofield.api.service.item.dto.response.*;
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

    @ApiOperation(value = "[인증] 상품을 좋아요/해제(를) 합니다")
    @Auth
    @PostMapping("/v1/like/{itemId}")
    public ApiResponse userLikeItem (@PathVariable Long itemId,
                                     @Valid @RequestBody ItemRequest.ItemLike request){
        itemService.userLikeItem(itemId, request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 내가 좋아요한 상품을 조회합니다")
    @Auth
    @GetMapping("/v1/like")
    public ApiResponse<List<ItemClassificationResponse>> retrieveUserLikeItems(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveUserLikeItemList(pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 최근 본 상품을 조회합니다")
    @Auth
    @GetMapping("/v1/recent")
    public ApiResponse<List<ItemClassificationResponse>> retrieveUserRecentItems(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveUserRecentItems(pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "인기 상품을 조회합니다")
    @GetMapping("/v1/popular")
    public ApiResponse<List<ItemBundlePopularResponse>> retrievePopularBundleItems(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrievePopularBundleItems(pageable));
    }

    @ApiOperation(value = "추천 상품을 조회합니다")
    @GetMapping("/v1/recommend")
    public ApiResponse<List<ItemBundleRecommendResponse>> retrieveRecommendBundleItems(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveRecommendBundleItems(pageable));
    }

    @ApiOperation(value ="추천 종목 묶음 상품을 조회합니다")
    @GetMapping("/v1/recommend/category")
    public ApiResponse<List<ItemBundleRecommendResponse>> retrieveRecommendCategoryBundleItems(@PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveRecommendBundleItems(pageable));
    }

    @ApiOperation(value = "새상품/중고 상품을 조회합니다")
    @GetMapping("/v1")
    public ApiResponse<List<ItemClassificationResponse>> retrieveItems(@RequestParam(required = false) EItemClassificationFlag classification,
                                                                       @RequestParam(required = false) Long categoryId,
                                                                       @RequestParam(required = false) List<EItemSpecFlag> spec,
                                                                       @RequestParam(required = false) EItemSort sort,
                                                                       @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveItems(classification, categoryId, spec,  sort, pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "카테고리별 묶음상품을 조회합니다")
    @GetMapping("/v1/bundle")
    public ApiResponse<List<ItemBundleCategoryResponse>> retrieveBundlesByCategory(@RequestParam Long categoryId,
                                                                                   @RequestParam(required = false) Long subCategoryId,
                                                                                   @RequestParam(required = false) EItemBundleSort sort,
                                                                                   @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveBundlesByCategory(categoryId, subCategoryId, sort, pageable));
    }

    @ApiOperation(value = "묶음 상품 상세를 조회합니다")
    @GetMapping("/v1/bundle/{bundleId}")
    public ApiResponse<ItemBundleResponse> retrieveBundle(@PathVariable Long bundleId){
        return ApiResponse.success(itemService.retrieveBundle(bundleId));
    }

    @ApiOperation(value = "묶음 상품에 해당하는 상품을 조회합니다")
    @GetMapping("/v1/bundle/{bundleId}/item")
    public ApiResponse<ItemClassificationPaginationResponse> retrieveBundleItems(@PathVariable Long bundleId,
                                                                                 @RequestParam(required = false) EItemClassificationFlag classification,
                                                                                 @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveBundleItems(bundleId, classification, pageable, UserIdResolver.getUserId()));
    }


    @ApiOperation(value = "묶음 상품에 해당하는 다른 (새상품/중고) 상품을 조회합니다")
    @GetMapping("/v1/bundle/{bundleId}/other/{itemId}")
    public ApiResponse<List<ItemClassificationResponse>> retrieveBundleOtherItems(@PathVariable Long bundleId,
                                                                                  @PathVariable Long itemId,
                                                                                  @RequestParam(required = false) EItemClassificationFlag classification,
                                                                                  @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveBundleOtherItems(bundleId, itemId, classification, pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "다른 추천상품(새상품/중고) 상품들을 조회합니다 ")
    @Auth(allowNonLogin = true)
    @GetMapping("/v1/bundle/{bundleId}/recommend/{itemId}")
    public ApiResponse<List<ItemClassificationResponse>> retrieveBundleRecommendItems(@PathVariable Long bundleId,
                                                                                      @PathVariable Long itemId,
                                                                                      @RequestParam Long categoryId,
                                                                                      @RequestParam(required = false) EItemClassificationFlag classification,
                                                                                      @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveBundleRecommendItems(bundleId, itemId, categoryId, classification, pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "다른 묶음상품에 해당하는 (새상품/중고) 상품들을 조회합니다 ")
    @Auth(allowNonLogin = true)
    @GetMapping("/v2/bundle/{bundleId}/other/{itemId}")
    public ApiResponse<List<ItemClassificationResponse>> retrieveBundleOtherItemsV2(@PathVariable Long bundleId,
                                                                                    @PathVariable Long itemId,
                                                                                    @RequestParam(required = false) EItemClassificationFlag classification,
                                                                                    @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveBundleOtherItemsV2(bundleId, itemId, classification, pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "묶음 상품에 리뷰를 조회합니다")
    @GetMapping("/v1/bundle/{bundleId}/review")
    public ApiResponse<ItemBundleReviewListResponse> retrieveBundleItemReviews(@PathVariable Long bundleId,
                                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveBundleItemReviews(bundleId, pageable));
    }

    @ApiOperation(value = "상품 상세를 조회합니다")
    @Auth(allowNonLogin = true)
    @GetMapping("/v1/{itemNumber}")
    public ApiResponse<ItemResponse> retrieveItem(@PathVariable String itemNumber){
        return ApiResponse.success(itemService.retrieveItem(itemNumber, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "상품 옵션을 조회합니다")
    @GetMapping("/v1/{itemId}/option")
    public ApiResponse<ItemOptionDetailResponse> retrieveItemOption(@PathVariable Long itemId){
        return ApiResponse.success(itemService.retrieveItemOption(itemId));
    }

    @ApiOperation(value = "상품 상세에 판매자 정보를 조회합니다")
    @GetMapping("/v1/{itemNumber}/seller")
    public ApiResponse<SellerShippingResponse> retrieveItemShippingTemplate(@PathVariable String itemNumber){
        return ApiResponse.success(itemService.retrieveItemShippingTemplate(itemNumber));
    }


    @ApiOperation(value = "상품 상세에 상품 문의를 조회합니다")
    @Auth(allowNonLogin = true)
    @GetMapping("/v1/{itemId}/qna")
    public ApiResponse<ItemQnaPaginationResponse> retrieveQnas(@PathVariable Long itemId,
                                                               @RequestParam(required = false, defaultValue = "false") Boolean isMe,
                                                               @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(itemService.retrieveQnas(itemId, isMe, pageable, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "상품 상세에 상품 문의 상세를 조회합니다")
    @Auth(allowNonLogin = true)
    @GetMapping("/v1/{itemId}/qna/{qnaId}")
    public ApiResponse<ItemQnaDetailResponse> retrieveQna(@PathVariable Long itemId,
                                                          @PathVariable Long qnaId){
        return ApiResponse.success(itemService.retrieveQna(itemId, qnaId, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "[인증] 상품 상세에 상품 문의를 등록합니다")
    @Auth
    @PostMapping("/v1/{itemId}/qna")
    public ApiResponse registerQna(@PathVariable Long itemId,
                                   @Valid @RequestBody ItemRequest.ItemQna request){
        itemService.registerQna(itemId, request, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }

    @ApiOperation(value = "[인증] 상품 상세에 상품 문의를 삭제합니다")
    @Auth
    @DeleteMapping("/v1/{itemId}}/qna/{qnaId}")
    public ApiResponse deleteQna(@PathVariable Long itemId,
                                 @PathVariable Long qnaId){
        itemService.deleteQna(itemId, qnaId, UserIdResolver.getUserId());
        return ApiResponse.SUCCESS;
    }
}
