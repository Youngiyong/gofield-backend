package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminSession;
import com.gofield.admin.dto.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        AdminSession admin = (AdminSession) request.getSession().getAttribute("sAdmin");
        if (admin == null) {
            return "login";
        } else {
            return "redirect:" + Constants.FIRST_PAGE_AFTER_LOGIN;
        }
    }
}
