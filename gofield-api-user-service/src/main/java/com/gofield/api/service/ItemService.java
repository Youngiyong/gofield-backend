package com.gofield.api.service;

import com.gofield.api.dto.req.ItemRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.item.ItemRepository;
import com.gofield.domain.rds.entity.itemBundle.ItemBundleRepository;
import com.gofield.domain.rds.entity.itemBundleReview.ItemBundleReview;
import com.gofield.domain.rds.entity.itemBundleReview.ItemBundleReviewRepository;
import com.gofield.domain.rds.entity.itemQna.ItemQna;
import com.gofield.domain.rds.entity.itemQna.ItemQnaRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItem;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItemRepository;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
import com.gofield.domain.rds.projections.ItemBundleRecommendProjection;
import com.gofield.domain.rds.projections.ItemClassificationProjection;
import com.gofield.domain.rds.projections.response.ItemBundleImageProjectionResponse;
import com.gofield.domain.rds.projections.response.ItemProjectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void userLikeItem(Long itemId, Boolean isLike){
        User user = userService.getUser();
        UserLikeItem userLikeItem = userLikeItemRepository.findByUserIdAndItemId(user.getId(), itemId);

        if(isLike){
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
    public List<ItemBundleCategoryResponse> getCategoryItemBundleList(Long categoryId, Long subCategoryId, Pageable pageable){
        List<ItemBundlePopularProjection> result = itemBundleRepository.findAllByCategoryId(categoryId, subCategoryId, pageable);
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
        List<ItemClassificationProjection> result = itemRepository.findAllUserLikeItemList(user.getId(), pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getClassificationItemList(EItemClassificationFlag classification, Long categoryId, Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjection> result = itemRepository.findAllClassificationItemByCategoryIdAndUserId(user.getId(), categoryId, classification, pageable);
        return ItemClassificationResponse.of(result);
    }

    @Transactional(readOnly = true)
    public ItemBundleResponse getBundleItemList(Long bundleId, Pageable pageable){
        User user = userService.getUser();
        ItemBundleImageProjectionResponse result = itemBundleRepository.findByBundleId(user.getId(), bundleId, pageable);
        return ItemBundleResponse.of(result);
    }

    @Transactional(readOnly = true)
    public List<ItemBundleReviewResponse> getBundleItemReviewList(Long bundleId, Pageable pageable){
        User user = userService.getUser();
        List<ItemBundleReview> result = itemBundleReviewRepository.findByBundleId(bundleId, pageable);
        return ItemBundleReviewResponse.of(result);
    }

    @Transactional(readOnly = true)
    public ItemResponse getItem(Long itemId){
        User user = userService.getUser();
        ItemProjectionResponse item = itemRepository.findByItemIdAndUserId(itemId, user.getId());
        return ItemResponse.of(item);
    }

    @Transactional(readOnly = true)
    public List<ItemQnaResponse> getQnaList(Long itemId, Boolean isMe, Pageable pageable){
        User user = userService.getUser();
        List<ItemQna> result = itemQnaRepository.findAllByItemIdAndUserId(itemId, isMe ? user.getId() : null, pageable);
        return ItemQnaResponse.of(result, user.getId());
    }

    @Transactional(readOnly = true)
    public ItemQnaDetailResponse getQna(Long itemId, Long qnaId){
        User user = userService.getUser();
        ItemQna result = itemQnaRepository.findByQnaIdAndItemId(qnaId, itemId);
        return ItemQnaDetailResponse.of(result, user.getId());
    }

    @Transactional
    public void insertQna(Long itemId, ItemRequest.ItemQna request){
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
