package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String getDashboard(HttpServletRequest request, Model model) {
        //임시 처리
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if(admin==null){
            return "login";
        }
        model.addAttribute("data", dashboardService.getDashboard());
        return "dashboard/index";

    }
}
