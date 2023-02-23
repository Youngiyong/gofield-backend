package com.gofield.api.service.search;

import com.gofield.api.service.item.dto.response.ItemClassificationListResponse;
import com.gofield.api.service.item.dto.response.ItemClassificationResponse;
import com.gofield.api.service.search.dto.response.PopularKeywordResponse;
import com.gofield.domain.rds.domain.item.ItemRepository;
import com.gofield.domain.rds.domain.item.projection.ItemListProjectionResponse;
import com.gofield.domain.rds.domain.search.PopularKeywordRepository;
import com.gofield.domain.rds.domain.search.SearchLog;
import com.gofield.domain.rds.domain.search.SearchLogRepository;
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
    private final ItemRepository itemRepository;
    private final SearchLogRepository searchLogRepository;
    private final PopularKeywordRepository popularKeywordRepository;

    @Transactional(readOnly = true)
    public List<PopularKeywordResponse> getPopularKeywordList(int size){
        return PopularKeywordResponse.of(popularKeywordRepository.findAllPopularKeywordList(size));
    }

//    @Transactional(readOnly = true)
//    public List<RecentKeywordResponse> getRecentKeywordList(int size){
//        return RecentKeywordResponse.of(searchLogRepository.findByUserIdLimit(user.getId(), size));
//    }

    @Transactional
    public ItemClassificationListResponse searchKeyword(String keyword, Pageable pageable, Long userId){
        ItemListProjectionResponse result =  itemRepository.findAllClassificationItemByKeyword(keyword, userId, pageable);
        Boolean isSearch = true;
        if(result.getList().isEmpty()){
            isSearch = false;
        }
        List<ItemClassificationResponse> itemList = ItemClassificationResponse.of(result.getList());
        SearchLog searchLog = SearchLog.newInstance(userId, keyword, isSearch);
        searchLogRepository.save(searchLog);
        return ItemClassificationListResponse.of(itemList, result.getTotalCount());
    }

}
