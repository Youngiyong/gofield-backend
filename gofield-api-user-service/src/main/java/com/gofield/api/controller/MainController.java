package com.gofield.api.controller;

import com.gofield.api.dto.res.BannerResponse;
import com.gofield.api.service.MainService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/main")
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @ApiOperation(value = "메인 배너 조회")
    @GetMapping("/{version}/banner")
    public ApiResponse<List<BannerResponse>> getBannerList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(mainService.getBannerList());
    }


}
