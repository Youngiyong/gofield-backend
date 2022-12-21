package com.gofield.admin.controller;

import com.gofield.admin.dto.BrandDto;
import com.gofield.admin.dto.BrandListDto;
import com.gofield.admin.service.BrandService;
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
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/brand")
    public String getBrandListPage(@RequestParam(required = false) String keyword,
                                   @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        BrandListDto result = brandService.getBrandList(keyword, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "brand/list";
    }

    @GetMapping("/brand/edit/{id}")
    public String getBrandEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        model.addAttribute("brand", brandService.getBrand(id));
        return "brand/edit";
    }

    @GetMapping("/brand/delete/{id}")
    public String deleteBrand(@PathVariable Long id){
        brandService.delete(id);
        return "redirect:/brand";
    }

    @PostMapping("/brand/edit")
    public String updateEditBrand(BrandDto brandDto){
        brandService.updateBrand(brandDto);
        return "redirect:/brand";
    }

    @GetMapping("/brand/add")
    public String getBrandAddPage(Model model){
        model.addAttribute("brand", new BrandDto());
        return "brand/add";
    }

    @PostMapping("/brand/add")
    public String addBrand(BrandDto brandDto){
        brandService.save(brandDto);
        return "redirect:/brand";
    }

}
