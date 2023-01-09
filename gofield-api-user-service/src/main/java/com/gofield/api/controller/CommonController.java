package com.gofield.api.controller;

import com.gofield.api.dto.res.*;
import com.gofield.api.service.CommonService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/common")
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @ApiOperation(value = "코드 조회")
    @GetMapping("/v1/code")
    public ApiResponse<List<CodeResponse>> getCodeList(@RequestParam ECodeGroup group,
                                                       @RequestParam(required = false, defaultValue = "false") Boolean isHide){
        return ApiResponse.success(commonService.getCodeList(group, isHide));
    }

    @ApiOperation(value = "카테고리 조회(카테고리 탭, 사용자 카테고리)")
    @GetMapping("/v1/category")
    public ApiResponse<List<CategoryResponse>> getCategoryList(){
        return ApiResponse.success(commonService.getCategoryList());
    }

    @ApiOperation(value = "서브 카테고리 조회")
    @GetMapping("/v1/category/{categoryId}")
    public ApiResponse<List<CategoryResponse>> getSubCategoryList(@PathVariable Long categoryId){
        return ApiResponse.success(commonService.getSubCategoryList(categoryId));
    }

    @ApiOperation(value = "공지사항 리스트")
    @GetMapping("/v1/notice")
    public ApiResponse<NoticeListResponse> getNoticeList(@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(commonService.getNoticeList(pageable));
    }

    @ApiOperation(value = "자주 묻는 질문 리스트")
    @GetMapping("/v1/faq")
    public ApiResponse<FaqListResponse> getFaqList(@RequestParam(required = false) String keyword, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(commonService.getFaqList(keyword, pageable));
    }
}
