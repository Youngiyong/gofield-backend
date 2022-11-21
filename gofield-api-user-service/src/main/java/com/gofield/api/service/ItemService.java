package com.gofield.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.dto.res.ItemRecentResponse;
import com.gofield.api.dto.res.MainItemResponse;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.item.ItemRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItem;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItemRepository;
import com.gofield.domain.rds.projections.ItemRecentProjection;
import com.gofield.domain.rds.projections.ItemUsedRecentProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${secret.cdn.url}")
    private String CDN_URL;
    private final UserService userService;
    private final ItemRepository itemRepository;
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
    public List<ItemRecentResponse> getRecentItem(Long categoryId, Pageable pageable){
        User user = userService.getUser();
        List<ItemRecentProjection> result = itemRepository.findAllRecentItemByCategoryIdAndUserId(user.getId(), categoryId, pageable);
        return result
                .stream()
                .map(p -> {
                    try {
                        return ItemRecentResponse.of(p.getId(), p.getItemNumber(), p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getLikeId(), p.getClassification(), p.getGender(),
                                new ObjectMapper().readValue(p.getOption(), new TypeReference<List<Map<String, Object>>>(){}
                                ));
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

}
