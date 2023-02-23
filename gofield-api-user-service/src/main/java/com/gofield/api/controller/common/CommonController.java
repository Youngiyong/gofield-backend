package com.gofield.api.controller.common;

import com.gofield.api.service.common.CommonService;
import com.gofield.api.service.common.dto.response.CategoryResponse;
import com.gofield.api.service.common.dto.response.CodeResponse;
import com.gofield.api.service.common.dto.response.FaqListResponse;
import com.gofield.api.service.common.dto.response.NoticeListResponse;
import com.gofield.common.model.dto.res.ApiResponse;
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

    @ApiOperation(value = "내부에 정의되어 있는 코드를 조회합니다")
    @GetMapping("/v1/code")
    public ApiResponse<List<CodeResponse>> retrieveCodes(@RequestParam ECodeGroup group,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean isHide){
        return ApiResponse.success(commonService.retrieveCodes(group, isHide));
    }

    @ApiOperation(value = "사용자 화면에 보여지는 카테고리 탭, 카테고리를 조회합니다")
    @GetMapping("/v1/category")
    public ApiResponse<List<CategoryResponse>> retrieveCategories(){
        return ApiResponse.success(commonService.retrieveCategories());
    }

    @ApiOperation(value = "사용자 화면에 보여지는 서브 카테고리를 조회합니다")
    @GetMapping("/v1/category/{categoryId}")
    public ApiResponse<List<CategoryResponse>> retrieveSubCategories(@PathVariable Long categoryId){
        return ApiResponse.success(commonService.retrieveSubCategories(categoryId));
    }

    @ApiOperation(value = "공지사항을 조회합니다")
    @GetMapping("/v1/notice")
    public ApiResponse<NoticeListResponse> retrieveNotices(@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(commonService.retrieveNotices(pageable));
    }

    @ApiOperation(value = "자주 묻는 질문을 조회합니다")
    @GetMapping("/v1/faq")
    public ApiResponse<FaqListResponse> retrieveFaqs(@RequestParam(required = false) String keyword, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return ApiResponse.success(commonService.retrieveFaqs(keyword, pageable));
    }
}
