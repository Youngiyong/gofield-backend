package com.gofield.api.controller;


import com.gofield.api.service.ThirdPartyService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/third")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    @ApiOperation(value = "테스트 - 카카오 로그인 페이지 이동")
    @GetMapping("/{version}/ready")
    public void thirdPartyReady(@PathVariable("version") EApiVersion apiVersion,
                                HttpServletResponse response) throws IOException {
        response.sendRedirect(thirdPartyService.redirect());
    }

    @ApiOperation(value = "테스트 - 카카오 인증 콜백")
    @GetMapping("/{version}/callback")
    @ResponseBody
    public String callbackLoginAuto(@PathVariable("version") EApiVersion apiVersion,
                                    @RequestParam String code,
                                    @RequestParam String state){
        return thirdPartyService.callbackLoginAuth(code, state);
    }
}
