package com.gofield.api.controller.main;

import com.gofield.api.service.main.dto.response.BannerListResponse;
import com.gofield.api.service.main.dto.response.MainResponse;
import com.gofield.api.service.main.MainService;
import com.gofield.common.model.dto.res.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/main")
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;


    @ApiOperation(value = "메인 배너를 조회합니다")
    @GetMapping("/v2/banner")
    public ApiResponse<BannerListResponse> retrieveBanners(){
        return ApiResponse.success(mainService.retrieveBanners());
    }

    @ApiOperation(value = "메인 상품을 조회합니다")
    @GetMapping("/v1/item")
    public ApiResponse<MainResponse> retrieveItems(){
        return ApiResponse.success(mainService.retrieveItems());
    }
}
