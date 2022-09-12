package com.gofield.api.controller;

import com.gofield.api.model.response.CategoryResponse;
import com.gofield.api.model.response.TermResponse;
import com.gofield.api.service.AppService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.enums.EPlatformFlag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/app")
@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;


    @GetMapping("/{version}/term")
    public ApiResponse<List<TermResponse>> getTermList(@PathVariable("version") EApiVersion apiVersion){

        return ApiResponse.success(appService.getTermList());
    }
    @GetMapping("/{version}/category")
    public ApiResponse<List<CategoryResponse>> getCategoryList(@PathVariable("version") EApiVersion apiVersion){

        return ApiResponse.success(appService.getCategoryList());
    }

    @GetMapping("/{version}/version")
    public ApiResponse versionCheck(@RequestHeader(value="app-version", required = false) String appVersion,
                                    @RequestHeader(value="platform", required = false) EPlatformFlag platform,
                                    @RequestHeader(value="os-version", required = false) String osVersion,
                                    @PathVariable("version") EApiVersion apiVersion) {
        return ApiResponse.success(appService.versionCheck(appVersion, platform, osVersion));
    }

    @GetMapping("/{version}/health")
    public ApiResponse healthCheck(@PathVariable("version") EApiVersion apiVersion) {
        appService.healthCheck();
        return ApiResponse.SUCCESS;
    }

}
