package com.gofield.api.controller;


import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.dto.res.OrderTempResponse;
import com.gofield.api.service.OrderService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.common.api.core.common.dto.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;







}
