package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.request.AdminRequest;
import com.gofield.admin.service.AdminService;
import com.gofield.domain.rds.entity.admin.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/add")
    public String add(Model model) {
        /*
        ToDo: 임시처리로 수정필요.
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if(admin==null){
            return "login";
        }
        model.addAttribute("admin", new AdminRequest.Create());
        return "admin/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
       /*
        ToDo: 임시처리로 수정필요.
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if(admin==null){
            return "login";
        }
        adminService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
       /*
        ToDo: 임시처리로 수정필요.
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");
        if(admin==null){
            return "login";
        }
        model.addAttribute("admin", adminService.findAdminById(id));
        return "admin/edit";
    }

    @PostMapping("/save")
    public String save(@Valid AdminRequest.Create request) {
        /*
        ToDo: 임시처리로 수정필요.
         */
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AdminDto admin = (AdminDto) servletRequest.getSession().getAttribute("sAdmin");
        if(admin==null){
            return "login";
        }
        adminService.save(request);
        return "redirect:/admin";
    }

    @GetMapping
    public String getAdminList(Model model) {
        /*
        ToDo: 임시처리로 수정필요.
         */
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AdminDto admin = (AdminDto) servletRequest.getSession().getAttribute("sAdmin");
        if(admin==null){
            return "login";
        }
        model.addAttribute("list", adminService.findAdminList());
        return "admin/list";
    }
}
