package com.gofield.api.controller;

import com.gofield.api.dto.res.BannerListResponse;
import com.gofield.api.dto.res.BannerResponse;
import com.gofield.api.dto.res.MainResponse;
import com.gofield.api.service.MainService;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/main")
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @ApiOperation(value = "메인 배너 조회")
    @GetMapping("/v1/banner")
    public ApiResponse<List<BannerResponse>> getBannerList(){
        return ApiResponse.success(mainService.getBannerList());
    }

    @ApiOperation(value = "메인 배너 조회 v2")
    @GetMapping("/v2/banner")
    public ApiResponse<BannerListResponse> getBannerListV2(){
        return ApiResponse.success(mainService.getBannerListV2());
    }

    @ApiOperation(value = "메인 상품 조회")
    @GetMapping("/v1/item")
    public ApiResponse<MainResponse> getMainItemList(){
        return ApiResponse.success(mainService.getMainItemList());
    }
}
