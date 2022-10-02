package com.gofield.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    @GetMapping
    public String getDashboard(HttpServletRequest request) {
        return "dashboard/index";
    }
}
