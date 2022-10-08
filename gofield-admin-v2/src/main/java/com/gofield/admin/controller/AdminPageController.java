package com.gofield.admin.controller;

import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-adminlte3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/11/20
 * Time: 08.41
 */
@Controller
public class AdminPageController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping({"/user/list", "/admin/user"})
    public String listUser() {
        return "user-list";
    }

    @GetMapping("/user/list2")
    public String listUser2(Model model) {
        model.addAttribute("users", adminRepository.findAll());
        return "user-list2";
    }

    @GetMapping("/user/add")
    public String showFormUser(Model model) {
        model.addAttribute("user", new Admin());
        return "user-add";
    }

    @PostMapping("/user/add")
    public String addUser(Model model, Admin user) {
        adminRepository.save(user);
        model.addAttribute("user", new Admin());
        return "user-list";
    }

}
