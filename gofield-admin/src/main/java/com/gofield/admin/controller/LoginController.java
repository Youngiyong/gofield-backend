package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.Constants;
import com.gofield.admin.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if (admin == null) {
            return "login";
        } else {
            return "redirect:" + Constants.FIRST_PAGE_AFTER_LOGIN;
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, Model model){

        return "redirect:" + Constants.FIRST_PAGE_AFTER_LOGIN;
    }
}
