package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminListDto;
import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private static final Integer POSTS_PER_PAGE = 10;
    private static final Integer PAGES_PER_BLOCK = 5;

    private final AdminService adminService;


    @GetMapping("/admin")
    public String getAdminListPage(@RequestParam(required = false) String keyword,
                                   @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        AdminListDto result =  adminService.findAllAdminList(keyword, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "admin/list";
    }

    @GetMapping("/admin/edit/{id}")
    public String getEditAdminPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        AdminDto admin = adminService.findAdminById(id);
        session.setAttribute("username", principal.getName());
        model.addAttribute("admin", admin);
        return "admin/edit";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable Long id){
        adminService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit")
    public String updateEditAdmin(AdminDto adminDto){
        adminService.updateAdmin(adminDto);
        return "redirect:/admin";
    }

    @GetMapping("/admin/add")
    public String getAdminAddPage(Model model){
        model.addAttribute("admin", new AdminDto());
        return "admin/add";
    }

    @PostMapping("/admin/add")
    public String addAdmin(AdminDto adminDto){
        adminService.save(adminDto);
        return "redirect:/admin";
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
