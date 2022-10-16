package com.gofield.admin.controller;

import com.gofield.admin.dto.response.AdminInfoResponse;
import com.gofield.admin.dto.response.PaginationResponse;
import com.gofield.admin.service.AdminService;
import com.gofield.admin.util.Paginator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private static final Integer POSTS_PER_PAGE = 10;
    private static final Integer PAGES_PER_BLOCK = 5;

    private final AdminService adminService;


    @GetMapping("/admin/list")
    public String adminList(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable,
                            Model model, HttpSession session,
                            Principal principal) {
        session.setAttribute("username", principal.getName());
        AdminInfoResponse response =  adminService.findAdminList(pageable);
        Paginator paginator = new Paginator(PAGES_PER_BLOCK, POSTS_PER_PAGE, response.getTotalElements());
        Map<String, Object> pageInfo = paginator.getFixedBlock(pageable.getPageNumber());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("list", paginationResponse.getData());
        model.addAttribute("pageNumbers", paginationResponse.getPageNumber());
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
