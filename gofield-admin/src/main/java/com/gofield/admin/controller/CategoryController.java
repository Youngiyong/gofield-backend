package com.gofield.admin.controller;

import com.gofield.admin.dto.CategoryDto;
import com.gofield.admin.dto.CategoryListDto;
import com.gofield.admin.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getCategoryListPage(@RequestParam(required = false) String keyword,
                                      @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        CategoryListDto result = categoryService.getCategoryList(keyword, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "category/list";
    }

    @GetMapping("/category/edit/{id}")
    public String getCategoryEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        model.addAttribute("category", categoryService.getCategory(id));
        return "category/edit";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/category";
    }

    @PostMapping("/category/edit")
    public String updateEditCategory(CategoryDto categoryDto){
        categoryService.updateCategory(categoryDto);
        return "redirect:/category";
    }

    @GetMapping("/category/add")
    public String getCategoryAddPage(Model model){
        model.addAttribute("category", categoryService.getCategory(null));
        return "category/add";
    }

    @PostMapping("/category/add")
    public String addCategory(CategoryDto categoryDto){
        categoryService.save(categoryDto);
        return "redirect:/category";
    }

}
