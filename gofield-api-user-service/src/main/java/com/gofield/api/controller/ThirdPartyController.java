package com.gofield.api.controller;

import com.gofield.api.service.ThirdPartyService;
import com.gofield.domain.rds.domain.common.EEnvironmentFlag;
import com.gofield.domain.rds.domain.user.ESocialFlag;
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
    @GetMapping("/v1/ready")
    public void thirdPartyReady(@RequestParam ESocialFlag social,
                                @RequestParam EEnvironmentFlag environment,
                                HttpServletResponse response) throws IOException {
        response.sendRedirect(thirdPartyService.redirect(social, environment));
    }

    @ApiOperation(value = "테스트 - 카카오 인증 콜백")
    @GetMapping("/v1/callback")
    public void callbackLoginAuto(@RequestParam String code,
                                  @RequestParam String state,
                                  HttpServletResponse response) throws IOException {
         response.sendRedirect(thirdPartyService.callbackAuth(code, state));
    }
}
