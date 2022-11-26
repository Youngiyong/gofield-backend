package com.gofield.api.controller;


import com.gofield.api.dto.res.CategoryResponse;
import com.gofield.api.dto.res.CodeResponse;
import com.gofield.api.service.CommonService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/code")
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @ApiOperation(value = "코드 조회")
    @GetMapping("/{version}/code")
    public ApiResponse<List<CodeResponse>> getCodeList(@PathVariable("version") EApiVersion apiVersion,
                                                       @RequestParam ECodeGroup group){
        return ApiResponse.success(commonService.getCodeList(group));
    }

    @ApiOperation(value = "카테고리 조회(카테고리 탭, 사용자 카테고리)")
    @GetMapping("/{version}/category")
    public ApiResponse<List<CategoryResponse>> getCategoryList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(commonService.getCategoryList());
    }

    @ApiOperation(value = "서브 카테고리 조회")
    @GetMapping("/{version}/category/{categoryId}")
    public ApiResponse<List<CategoryResponse>> getSubCategoryList(@PathVariable("version") EApiVersion apiVersion,
                                                                  @PathVariable Long categoryId){
        return ApiResponse.success(commonService.getSubCategoryList(categoryId));
    }

}
