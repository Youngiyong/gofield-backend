package com.gofield.api.service;

import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.item.ItemRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItem;
import com.gofield.domain.rds.entity.userLikeItem.UserLikeItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

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


}
