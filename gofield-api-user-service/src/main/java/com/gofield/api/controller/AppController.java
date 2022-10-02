package com.gofield.api.controller;

import com.gofield.api.dto.enums.TermType;
import com.gofield.api.dto.response.BannerResponse;
import com.gofield.api.dto.response.CategoryResponse;
import com.gofield.api.dto.response.TermResponse;
import com.gofield.api.service.AppService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.enums.EPlatformFlag;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/app")
@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @ApiOperation(value = "이용약관 리스트 조회")
    @GetMapping("/{version}/term")
    public ApiResponse<List<TermResponse>> getTermList(@PathVariable("version") EApiVersion apiVersion,
                                                       @RequestParam TermType type){
        return ApiResponse.success(appService.getTermList(type));
    }

    @ApiOperation(value = "배너 리스트 조회")
    @GetMapping("/{version}/banner")
    public ApiResponse<List<BannerResponse>> getBannerList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(appService.getBannerList());
    }

    @ApiOperation(value = "카테고리 리스트 조회")
    @GetMapping("/{version}/category")
    public ApiResponse<List<CategoryResponse>> getCategoryList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(appService.getCategoryList());
    }

    @ApiOperation(value = "버전 체크 - 스플레쉬")
    @GetMapping("/{version}/version")
    public ApiResponse versionCheck(@RequestHeader(value="app-version", required = false) String appVersion,
                                    @RequestHeader(value="platform", required = false) EPlatformFlag platform,
                                    @PathVariable("version") EApiVersion apiVersion) {
        return ApiResponse.success(appService.versionCheck(appVersion, platform));
    }

    @ApiOperation(value = "서버 점검 체크 - 스플래쉬")
    @GetMapping("/{version}/health")
    public ApiResponse healthCheck(@PathVariable("version") EApiVersion apiVersion) {
        appService.healthCheck();
        return ApiResponse.SUCCESS;
    }

}
