package com.gofield.api.service.item;

import com.gofield.api.service.item.dto.request.ItemRequest;
import com.gofield.api.service.item.dto.response.*;
import com.gofield.api.service.thirdparty.ThirdPartyService;
import com.gofield.api.service.user.UserService;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.CommonUtils;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.domain.rds.domain.common.PaginationResponse;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.*;
import com.gofield.domain.rds.domain.seller.Seller;
import com.gofield.domain.rds.domain.seller.SellerRepository;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.user.UserLikeItem;
import com.gofield.domain.rds.domain.user.UserLikeItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemQnaRepository itemQnaRepository;
    private final ItemBundleRepository itemBundleRepository;
    private final UserLikeItemRepository userLikeItemRepository;
    private final ItemBundleReviewRepository itemBundleReviewRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final SellerRepository sellerRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemRecentRepository itemRecentRepository;
    private final ItemOptionGroupRepository itemOptionGroupRepository;
    private final ThirdPartyService thirdPartyService;

    @Transactional
    public void userLikeItem(Long itemId, ItemRequest.ItemLike request, Long userId){
        User user = userService.getUser(userId);
        UserLikeItem userLikeItem = userLikeItemRepository.findByUserIdAndItemId(userId, itemId);
        if(request.getIsLike()){
            if(userLikeItem==null){
                Item item = itemRepository.findByItemId(itemId);
                if(item==null) throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 상품 아이디입니다.", itemId));
                userLikeItem = UserLikeItem.newInstance(user, item);
                userLikeItemRepository.save(userLikeItem);
            }
        } else {
            if(userLikeItem!=null){
                userLikeItemRepository.delete(userLikeItem);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ItemBundleCategoryResponse> retrieveBundlesByCategory(Long categoryId, Long subCategoryId, EItemBundleSort sort, Pageable pageable){
        List<ItemBundlePopularProjection> result = itemBundleRepository.findAllByCategoryId(categoryId, subCategoryId, sort, pageable);
        return ItemBundleCategoryResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemBundlePopularResponse> retrievePopularBundleItems(Pageable pageable){
        List<ItemBundlePopularProjection> result = itemBundleRepository.findAllPopularBundleItemList(pageable);
        return ItemBundlePopularResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemBundleRecommendResponse> retrieveRecommendBundleItems(Pageable pageable){
        List<ItemBundleRecommendProjection> result = itemBundleRepository.findAllRecommendBundleItemList(pageable);
        return ItemBundleRecommendResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> retrieveUserLikeItemList(Pageable pageable, Long userId){
        User user = userService.getUser(userId);
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllUserLikeItemList(user.getId(), pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> retrieveUserRecentItems(Pageable pageable, Long userId){
        User user = userService.getUser(userId);
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllRecentItemByUserId(user.getId(), pageable);
        return ItemClassificationResponse.of(result);
    }


    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> retrieveItems(EItemClassificationFlag classification, Long categoryId, List<EItemSpecFlag> spec, EItemSort sort, Pageable pageable, Long userId){
        List<Long> categoryIdList = null;
        if(categoryId!=null){
            List<Category> categoryList = categoryRepository.findAllSubCategoryByCategoryId(categoryId);
            if(categoryList!=null){
                categoryIdList = categoryList.stream().map(p -> p.getId()).collect(Collectors.toList());
            }
        }
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllClassificationItemByCategoryIdAndUserId(userId, classification, categoryIdList, spec, sort, pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional
    public ItemBundleResponse retrieveBundle(Long bundleId){
        ItemBundleImageProjectionResponse result = itemBundleRepository.findAggregationByBundleId(bundleId);
        if(result==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "<%s>는 존재하지 않는 묶음 상품입니다.");
        }
        return ItemBundleResponse.of(result);
    }

    @Transactional(readOnly = true)
    public ItemClassificationPaginationResponse retrieveBundleItems(Long bundleId, EItemClassificationFlag classification, Pageable pageable, Long userId){
        Page<ItemClassificationProjectionResponse> result = itemBundleRepository.findAllItemByBundleIdAndClassification(userId, bundleId, classification, pageable);
        List<ItemClassificationResponse> list = ItemClassificationResponse.of(result.getContent());
        return ItemClassificationPaginationResponse.of(list, PaginationResponse.of(result));
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> retrieveBundleOtherItems(Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable, Long userId){
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllClassificationItemByBundleIdAndClassificationAndNotNqItemId(userId, bundleId, itemId, classification, pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> retrieveBundleRecommendItems(Long bundleId, Long itemId, Long categoryId, EItemClassificationFlag classification, Pageable pageable, Long userId){
        return ItemClassificationResponse.of(itemRepository.findAllByUserIdAndCategoryIdAndClassificationAndNeItemId(userId, categoryId, itemId, classification, pageable));
    }
    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> retrieveBundleOtherItemsV2(Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable, Long userId){
        return ItemClassificationResponse.of(itemRepository.findAllByUserIdAndBundleIdAndClassificationAndNeItemId(userId, bundleId, itemId, classification, pageable));
    }


    @Transactional(readOnly = true)
    public ItemBundleReviewListResponse retrieveBundleItemReviews(Long bundleId, Pageable pageable){
        Page<ItemBundleReview> result = itemBundleReviewRepository.findByBundleId(bundleId, pageable);
        List<ItemBundleReviewResponse> list = ItemBundleReviewResponse.of(result.getContent());
        return ItemBundleReviewListResponse.of(list, PaginationResponse.of(result));
    }

    @Transactional
    public ItemResponse retrieveItem(String itemNumber, Long userId){
        ItemProjectionResponse item = itemRepository.findByItemNumberAndUserId(itemNumber, userId);
        if(item==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 존재하지 않는 상품번호입니다.", itemNumber));
        }
        //비회원 분기처리
        if(userId!=null){
            ItemRecent itemRecent = itemRecentRepository.findByItemNumberAndUserId(item.getItemNumber(), userId);
            if(itemRecent==null){
                itemRecent = ItemRecent.newInstance(userId, item.getId(), itemNumber);
                itemRecentRepository.save(itemRecent);
            } else {
                itemRecent.update();
            }
        }
        return ItemResponse.of(item);
    }

    @Transactional(readOnly = true)
    public ItemOptionDetailResponse retrieveItemOption(Long itemId){
        List<ItemOptionGroup> itemOptionGroupList = itemOptionGroupRepository.findAllItemOptionGroupByItemId(itemId);
        if(itemOptionGroupList.isEmpty()){
            return ItemOptionDetailResponse.of(null, null);
        }
        List<ItemOptionProjection> itemOptionProjectionList = itemOptionRepository.findAllItemOptionByItemId(itemId);
        return ItemOptionDetailResponse.of(ItemOptionGroupResponse.of(itemOptionGroupList), ItemOptionResponse.of(itemOptionProjectionList));
    }


    @Transactional(readOnly = true)
    public SellerShippingResponse retrieveItemShippingTemplate(String itemNumber){
        ItemStock itemStock = itemStockRepository.findShippingTemplateByItemNumberFetch(itemNumber);
        if(itemStock==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "<%s>는 존재하지 않는 상품번호입니다.");
        }
        Seller seller = sellerRepository.findBySellerId(itemStock.getSellerId());
        return SellerShippingResponse.of(seller, ShippingTemplateResponse.of(itemStock.getItem().getShippingTemplate()));
    }

    @Transactional(readOnly = true)
    public ItemQnaPaginationResponse retrieveQnas(Long itemId, Boolean isMe, Pageable pageable, Long userId){
        Page<ItemQna> result = itemQnaRepository.findAllByItemIdAndUserId(itemId, isMe ? userId : null, pageable);
        List<ItemQnaResponse> response = ItemQnaResponse.of(result.getContent(), userId);
        return ItemQnaPaginationResponse.of(response, PaginationResponse.of(result));
    }

    @Transactional(readOnly = true)
    public ItemQnaDetailResponse retrieveQna(Long itemId, Long qnaId, Long userId){
        ItemQna result = itemQnaRepository.findByQnaIdAndItemId(qnaId, itemId);
        if(result==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 상품 문의입니다.");
        }
        Seller seller = sellerRepository.findBySellerId(result.getItem().getSellerId());
        return ItemQnaDetailResponse.of(result, userId, seller);
    }

    @Transactional
    public void registerQna(Long itemId, ItemRequest.ItemQna request, Long userId){
        User user = userService.getUser(userId);
        Item item = itemRepository.findByItemId(itemId);
        ItemQna qna = ItemQna.newInstance(item, user, user.getNickName()==null ? user.getName() : user.getNickName(), request.getTitle(), request.getDescription(), request.getIsVisible() == null ? true : request.getIsVisible());
        item.addQna(qna);
        thirdPartyService.sendItemQnaNotification(user.getName()==null ? user.getNickName() : user.getName(), item.getItemNumber(), item.getName(), CommonUtils.makeCloudFrontAdminUrl(item.getThumbnail()), qna.getTitle(), LocalDateTimeUtils.LocalDateTimeNowToString());
    }

    @Transactional
    public void deleteQna(Long itemId, Long qnaId, Long userId){
        User user = userService.getUser(userId);
        ItemQna itemQna = itemQnaRepository.findByQnaIdAndUserId(qnaId, user.getId());
        if(itemQna==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "삭제처리 되었거나 삭제가 불가능한 문의입니다.");
        }
        itemQnaRepository.delete(itemQna);
    }
}
