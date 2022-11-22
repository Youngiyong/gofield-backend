package com.gofield.api.service;

import com.gofield.api.dto.res.CategoryResponse;
import com.gofield.api.dto.res.ItemBundlePopularResponse;
import com.gofield.api.dto.res.ItemBundleRecommendResponse;
import com.gofield.api.dto.res.ItemClassificationResponse;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.category.CategoryRepository;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.item.ItemRepository;
import com.gofield.domain.rds.entity.itemBundle.ItemBundleRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItem;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItemRepository;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
import com.gofield.domain.rds.projections.ItemBundleRecommendProjection;
import com.gofield.domain.rds.projections.ItemClassificationProjection;
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

    private final CategoryRepository categoryRepository;
    private final ItemBundleRepository itemBundleRepository;
    private final UserLikeItemRepository userLikeItemRepository;

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
    public List<CategoryResponse> getItemCategoryList(){
        return CategoryResponse.of(categoryRepository.findAllIsActiveAndIsAttentionOrderBySort());
    }

    @Transactional(readOnly = true)
    public List<ItemBundlePopularResponse> getPopularItemBundleList(){
        List<ItemBundlePopularProjection> result = itemBundleRepository.findAllPopularBundleItemList();
        return ItemBundlePopularResponse.ofList(result);
    }

    @Transactional(readOnly = true)
    public List<ItemBundleRecommendResponse> getRecommendItemBundleList(){
        List<ItemBundleRecommendProjection> result = itemBundleRepository.findAllRecommendBundleItemList();
        return ItemBundleRecommendResponse.ofList(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getUserLikeItemList(Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjection> result = itemRepository.findAllUserLikeItemList(user.getId(), pageable);
        return ItemClassificationResponse.ofList(result);
    }

    @Transactional(readOnly = true)
    public List<ItemClassificationResponse> getClassificationItemList(EItemClassificationFlag classification, Long categoryId, Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjection> result = itemRepository.findAllClassificationItemByCategoryIdAndUserId(user.getId(), categoryId, classification, pageable);
        return ItemClassificationResponse.ofList(result);
    }

}
