package com.gofield.admin.controller;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.AdminListDto;
import com.gofield.admin.dto.response.UserDto;
import com.gofield.admin.dto.response.UserListDto;
import com.gofield.admin.service.UserService;
import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String getUserListPage(@RequestParam(required = false) String keyword,
                                   @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        UserListDto result = userService.getUserList(keyword, pageable);

        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "user/list";
    }


    @GetMapping("/user/edit/{id}")
    public String getUserEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        UserDto user = userService.getUser(id);
        session.setAttribute("username", principal.getName());
        model.addAttribute("user", user);
        return "user/edit";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/user";
    }

    @PostMapping("/user/edit")
    public String updateEditAdmin(UserDto userDto){
        userService.updateUser(userDto);
        return "redirect:/user";
    }
}
