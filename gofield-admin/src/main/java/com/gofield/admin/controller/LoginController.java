package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.Constants;
import com.gofield.admin.dto.request.LoginRequest;
import com.gofield.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @GetMapping
    public String rootPage(HttpServletRequest request) {
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if (admin == null) {
            return "login";
        } else {
            return "redirect:" + Constants.LOGIN_AFTER_PAGE;
        }
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if (admin == null) {
            return "login";
        } else {
            return "redirect:" + Constants.LOGIN_AFTER_PAGE;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(LoginRequest request, HttpServletRequest servletRequest){
        HttpHeaders headers = authService.login(request, servletRequest);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
