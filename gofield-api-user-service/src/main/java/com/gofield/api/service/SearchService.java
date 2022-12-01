package com.gofield.api.service;

import com.gofield.api.dto.res.ItemClassificationListResponse;
import com.gofield.api.dto.res.ItemClassificationResponse;
import com.gofield.api.dto.res.PopularKeywordResponse;
import com.gofield.domain.rds.domain.item.ItemRepository;
import com.gofield.domain.rds.domain.item.projection.ItemListProjectionResponse;
import com.gofield.domain.rds.domain.search.PopularKeywordRepository;
import com.gofield.domain.rds.domain.search.SearchLog;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.search.SearchLogRepository;
import com.gofield.domain.rds.domain.item.projection.ItemClassificationProjectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final SearchLogRepository searchLogRepository;
    private final PopularKeywordRepository popularKeywordRepository;

    @Transactional(readOnly = true)
    public List<PopularKeywordResponse> getPopularKeywordList(int size){
        return PopularKeywordResponse.of(popularKeywordRepository.findAllPopularKeywordList(size));
    }

    @Transactional
    public ItemClassificationListResponse searchKeyword(String keyword, Pageable pageable){
        User user = userService.getUser();
        ItemListProjectionResponse result =  itemRepository.findAllClassificationItemByKeyword(keyword, user.getId(), pageable);
        Boolean isSearch = true;
        if(result.getList().isEmpty()){
            isSearch = false;
        }
        List<ItemClassificationResponse> itemList = ItemClassificationResponse.of(result.getList());
        SearchLog searchLog = SearchLog.newInstance(user.getId(), keyword, isSearch);
        searchLogRepository.save(searchLog);
        return ItemClassificationListResponse.of(itemList, result.getTotalCount());
    }

}
