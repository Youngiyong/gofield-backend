package com.gofield.api.controller;


import com.gofield.api.dto.res.BannerResponse;
import com.gofield.api.dto.res.CodeResponse;
import com.gofield.api.service.CommonService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.domain.rds.enums.ECodeGroup;
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
}
