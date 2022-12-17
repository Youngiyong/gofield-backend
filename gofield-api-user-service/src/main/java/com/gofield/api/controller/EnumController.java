package com.gofield.api.controller;

import com.gofield.common.api.core.common.dto.response.ApiResponse;
import com.gofield.common.model.EnumValue;
import com.gofield.common.utils.EnumMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/enum")
@RestController
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @ApiOperation(value = "클라이언트에서 사용하는 enum 조회")
    @GetMapping("/v1/{key}")
    public ApiResponse<List<EnumValue>> getEnum(@PathVariable String key){
        return ApiResponse.success(enumMapper.get(key));
    }

    @ApiOperation(value = "클라이언트에 사용하는 enum 목록 전체 반환")
    @GetMapping("/v1")
    public ApiResponse<Map<String, List<EnumValue>>>  getEnumList(){
        return ApiResponse.success(enumMapper.getAll());
    }
}
