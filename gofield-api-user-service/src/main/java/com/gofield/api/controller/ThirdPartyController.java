package com.gofield.api.controller;

import com.gofield.api.service.ThirdPartyService;
import com.gofield.common.api.core.common.dto.enums.EApiVersion;
import com.gofield.domain.rds.enums.EEnvironmentFlag;
import com.gofield.domain.rds.enums.ESocialFlag;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/third")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    @ApiOperation(value = "소셜 로그인 페이지 이동")
    @GetMapping("/{version}/ready")
    public void thirdPartyReady(@PathVariable("version") EApiVersion apiVersion,
                                @RequestParam ESocialFlag social,
                                @RequestParam EEnvironmentFlag environment,
                                HttpServletResponse response) throws IOException {
        response.sendRedirect(thirdPartyService.redirect(social, environment));
    }

    @ApiOperation(value = "테스트 - 카카오 인증 콜백")
    @GetMapping("/{version}/callback")
    public void callbackLoginAuto(@PathVariable("version") EApiVersion apiVersion,
                                  @RequestParam String code,
                                  @RequestParam String state,
                                  HttpServletResponse response) throws IOException {
         response.sendRedirect(thirdPartyService.callbackAuth(code, state));
    }
}
