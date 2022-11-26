package com.gofield.api.service;

import com.gofield.api.dto.res.ItemClassificationResponse;
import com.gofield.api.dto.res.PopularKeywordResponse;
import com.gofield.domain.rds.entity.item.ItemRepository;
import com.gofield.domain.rds.entity.popularKeyword.PopularKeyword;
import com.gofield.domain.rds.entity.popularKeyword.PopularKeywordRepository;
import com.gofield.domain.rds.entity.searchLog.SearchLog;
import com.gofield.domain.rds.entity.topKeyword.TopKeyword;
import com.gofield.domain.rds.entity.topKeyword.TopKeywordRepository;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.entity.searchLog.SearchLogRepository;
import com.gofield.domain.rds.projections.response.ItemClassificationProjectionResponse;
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
    public List<ItemClassificationResponse> searchKeyword(String keyword, Pageable pageable){
        User user = userService.getUser();
        List<ItemClassificationProjectionResponse> result =  itemRepository.findAllClassificationItemByKeyword(keyword, user.getId(), pageable);
        Boolean isSearch = true;
        if(result.isEmpty()){
            isSearch = false;
        }
        SearchLog searchLog = SearchLog.newInstance(user.getId(), keyword, isSearch);
        searchLogRepository.save(searchLog);
        return ItemClassificationResponse.of(result);
    }

}
