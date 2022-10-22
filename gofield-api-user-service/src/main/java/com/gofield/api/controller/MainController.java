package com.gofield.api.controller;

import com.gofield.api.dto.res.MainResponse;
import com.gofield.api.service.MainService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.common.exception.ForbiddenException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/main")
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @ApiOperation(value = "메인 컨텐츠 조회")
    @GetMapping("/{version}/content")
    public ApiResponse<MainResponse> getBannerList(@PathVariable("version") EApiVersion apiVersion){
        return ApiResponse.success(mainService.getMainContentList());
    }

    @GetMapping("/{version}/error3")
    public ApiResponse  error(@PathVariable("version") EApiVersion apiVersion){
        throw new ForbiddenException(ErrorCode.E403_FORBIDDEN_EXCEPTION, ErrorAction.TOAST, ErrorCode.E403_FORBIDDEN_EXCEPTION.getMessage());
    }
}
