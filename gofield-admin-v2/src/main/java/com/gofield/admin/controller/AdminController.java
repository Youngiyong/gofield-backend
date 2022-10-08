package com.gofield.admin.controller;

import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin/list")
    public String adminList(HttpSession session, Principal principal) {
        session.setAttribute("username", principal.getName());
        System.out.println();
        return "admin/list";
    }

//    @GetMapping("/user/list2")
//    public String listUser2(Model model) {
//        model.addAttribute("users", adminRepository.findAll());
//        return "user-list2";
//    }
//
//    @GetMapping("/user/add")
//    public String showFormUser(Model model) {
//        model.addAttribute("user", new Admin());
//        return "user-add";
//    }
//
//    @PostMapping("/user/add")
//    public String addUser(Model model, Admin user) {
//        adminRepository.save(user);
//        model.addAttribute("user", new Admin());
//        return "user-list";
//    }

}
