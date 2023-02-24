package com.gofield.api.controller.search;

import com.gofield.api.config.interceptor.Auth;
import com.gofield.api.config.resolver.UserIdResolver;
import com.gofield.api.service.item.dto.response.ItemClassificationListResponse;
import com.gofield.api.service.search.dto.response.PopularKeywordResponse;
import com.gofield.api.service.search.SearchService;
import com.gofield.api.service.search.dto.response.RecentKeywordResponse;
import com.gofield.common.model.dto.res.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "인기 검색어를 조회합니다.")
    @GetMapping("/v1/keyword")
    public ApiResponse<List<PopularKeywordResponse>> getPopularKeywordList(@RequestParam(required = false, defaultValue = "5") Integer size){
        return ApiResponse.success(searchService.getPopularKeywordList(size));
    }

    @ApiOperation(value = "최근 검색어 조회")
    @Auth(allowNonLogin = true)
    @GetMapping("/v1/recent")
    public ApiResponse<List<RecentKeywordResponse>> getRecentKeywordList(@RequestParam(required = false, defaultValue = "5") Integer size){
        return ApiResponse.success(searchService.getRecentKeywordList(size, UserIdResolver.getUserId()));
    }

    @ApiOperation(value = "검색")
    @Auth(allowNonLogin = true)
    @GetMapping("/v1")
    public ApiResponse<ItemClassificationListResponse> getSearchList(@RequestParam String keyword,
                                                                     @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(searchService.searchKeyword(keyword, pageable, UserIdResolver.getUserId()));
    }

}
