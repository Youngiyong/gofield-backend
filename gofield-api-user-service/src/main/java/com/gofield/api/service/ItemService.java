package com.gofield.api.service;

import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.ForbiddenException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final UserService userService;
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

    @Transactional
    public void userLikeItem(Long itemId, ItemRequest.ItemLike request){
        User user = userService.getUser();
        if(user.getId()==null){
            throw new ForbiddenException(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.TOAST, "비회원은 좋아요가 불가합니다.");
        }
        UserLikeItem userLikeItem = userLikeItemRepository.findByUserIdAndItemId(user.getId(), itemId);
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
    public List<ItemBundleCategoryResponse> getCategoryItemBundleList(Long categoryId, Long subCategoryId, EItemBundleSort sort, Pageable pageable){
        List<ItemBundlePopularProjection> result = itemBundleRepository.findAllByCategoryId(categoryId, subCategoryId, sort, pageable);
        return ItemBundleCategoryResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemBundlePopularResponse> getPopularItemBundleList(Pageable pageable){
        List<ItemBundlePopularProjection> result = itemBundleRepository.findAllPopularBundleItemList(pageable);
        return ItemBundlePopularResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemBundleRecommendResponse> getRecommendItemBundleList(Pageable pageable){
        List<ItemBundleRecommendProjection> result = itemBundleRepository.findAllRecommendBundleItemList(pageable);
        return ItemBundleRecommendResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getUserLikeItemList(Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllUserLikeItemList(user.getId(), pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getUserRecentItemList(Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllRecentItemByUserId(user.getId());
        return ItemClassificationResponse.of(result);
    }


    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getClassificationItemList(EItemClassificationFlag classification, List<Long> categoryId, List<EItemSpecFlag> spec, List<EItemSort> sort,  Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllClassificationItemByCategoryIdAndUserId(user.getId(), classification, categoryId, spec, sort, pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional
    public ItemBundleResponse getBundle(Long bundleId){
        ItemBundleImageProjectionResponse result = itemBundleRepository.findAggregationByBundleId(bundleId);
        if(result==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "<%s>는 존재하지 않는 묶음 상품입니다.");
        }
        return ItemBundleResponse.of(result);
    }

    @Transactional(readOnly = true)
    public ItemClassificationPaginationResponse getBundleItemList(Long bundleId, EItemClassificationFlag classification, Pageable pageable){
        User user = userService.getUser();
        Page<ItemClassificationProjectionResponse> result = itemBundleRepository.findAllItemByBundleIdAndClassification(user.getId(), bundleId, classification, pageable);
        List<ItemClassificationResponse> list = ItemClassificationResponse.of(result.getContent());
        return ItemClassificationPaginationResponse.of(list, PaginationResponse.of(result));
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getOtherItemList(Long bundleId, Long itemId,  EItemClassificationFlag classification, Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjectionResponse> result = itemRepository.findAllClassificationItemByBundleIdAndClassificationAndNotNqItemId(user.getId(), bundleId, itemId, classification, pageable);
        return ItemClassificationResponse.of(result);
    }


    @Transactional(readOnly = true)
    public ItemBundleReviewListResponse getBundleItemReviewList(Long bundleId, Pageable pageable){
        User user = userService.getUser();
        Page<ItemBundleReview> result = itemBundleReviewRepository.findByBundleId(bundleId, pageable);
        List<ItemBundleReviewResponse> list = ItemBundleReviewResponse.of(result.getContent());
        return ItemBundleReviewListResponse.of(list, PaginationResponse.of(result));
    }

    @Transactional
    public ItemResponse getItem(String itemNumber){
        User user = userService.getUser();
        ItemProjectionResponse item = itemRepository.findByItemNumberAndUserId(itemNumber, user.getId());
        if(item==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 존재하지 않는 상품번호입니다.", itemNumber));
        }
        ItemRecent itemRecent = itemRecentRepository.findByItemNumberAndUserId(item.getItemNumber(), user.getId());
        if(itemRecent==null){
            itemRecent = ItemRecent.newInstance(user.getId(), item.getId(), itemNumber);
            itemRecentRepository.save(itemRecent);
        } else {
            itemRecent.update();
        }
        return ItemResponse.of(item);
    }

    @Transactional(readOnly = true)
    public ItemOptionDetailResponse getItemOption(Long itemId){
        User user = userService.getUser();
        List<ItemOptionGroup> itemOptionGroupList = itemOptionGroupRepository.findAllItemOptionGroupByItemId(itemId);
        if(itemOptionGroupList.isEmpty()){
            return ItemOptionDetailResponse.of(null, null);
        }
        List<ItemOptionProjection> itemOptionProjectionList = itemOptionRepository.findAllItemOptionByItemId(itemId);
        return ItemOptionDetailResponse.of(ItemOptionGroupResponse.of(itemOptionGroupList), ItemOptionResponse.of(itemOptionProjectionList));
    }

    @Transactional(readOnly = true)
    public SellerShippingResponse getItemSeller(String itemNumber){
        User user = userService.getUser();
        ItemStock itemStock = itemStockRepository.findShippingTemplateByItemNumberFetch(itemNumber);
        if(itemStock==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "<%s>는 존재하지 않는 상품번호입니다.");
        }
        Seller seller = sellerRepository.findBySellerId(itemStock.getSellerId());
        return SellerShippingResponse.of(seller, ShippingTemplateResponse.of(itemStock.getItem().getShippingTemplate()));
    }

    @Transactional(readOnly = true)
    public ItemQnaPaginationResponse getQnaList(Long itemId, Boolean isMe, Pageable pageable){
        User user = userService.getUser();
        Page<ItemQna> result = itemQnaRepository.findAllByItemIdAndUserId(itemId, isMe ? user.getId() : null, pageable);
        List<ItemQnaResponse> response = ItemQnaResponse.of(result.getContent(), user.getId());
        return ItemQnaPaginationResponse.of(response, PaginationResponse.of(result));
    }

    @Transactional(readOnly = true)
    public ItemQnaDetailResponse getQna(Long itemId, Long qnaId){
        User user = userService.getUser();
        ItemQna result = itemQnaRepository.findByQnaIdAndItemId(qnaId, itemId);
        return ItemQnaDetailResponse.of(result, user.getId());
    }

    @Transactional
    public void createItemQna(Long itemId, ItemRequest.ItemQna request){
        User user = userService.getUser();
        Item item = itemRepository.findByItemId(itemId);
        ItemQna qna = ItemQna.newInstance(item, user, user.getNickName()==null ? user.getName() : user.getNickName(), request.getTitle(), request.getDescription(), request.getIsVisible() == null ? true : request.getIsVisible());
        itemQnaRepository.save(qna);
    }

    @Transactional
    public void deleteQna(Long itemId, Long qnaId){
        User user = userService.getUser();
        ItemQna itemQna = itemQnaRepository.findByQnaIdAndUserId(qnaId, user.getId());
        if(itemQna==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "삭제처리 되었거나 삭제가 불가능한 qna입니다.");
        }
        itemQnaRepository.delete(itemQna);
    }

}
