package com.gofield.api.controller;

import com.gofield.api.dto.res.ItemClassificationListResponse;
import com.gofield.api.dto.res.PopularKeywordResponse;
import com.gofield.api.dto.res.RecentKeywordResponse;
import com.gofield.api.service.SearchService;
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

    @ApiOperation(value = "인기 검색어 조회(임시)")
    @GetMapping("/v1/keyword")
    public ApiResponse<List<PopularKeywordResponse>> getPopularKeywordList(@RequestParam(required = false, defaultValue = "5") Integer size){
        return ApiResponse.success(searchService.getPopularKeywordList(size));
    }

    @ApiOperation(value = "최근 검색어 조회(임시)")
    @GetMapping("/v1/recent")
    public ApiResponse<List<RecentKeywordResponse>> getRecentKeywordList(@RequestParam(required = false, defaultValue = "5") Integer size){
        return ApiResponse.success(searchService.getRecentKeywordList(size));
    }


    @ApiOperation(value = "검색")
    @GetMapping("/v1")
    public ApiResponse<ItemClassificationListResponse> getSearchList(@RequestParam String keyword,
                                                                     @PageableDefault(sort="createDate", direction = Sort.Direction.ASC) Pageable pageable){
        return ApiResponse.success(searchService.searchKeyword(keyword, pageable));
    }

}
