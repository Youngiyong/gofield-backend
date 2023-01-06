package com.gofield.admin.controller;

import com.gofield.admin.dto.BannerDto;
import com.gofield.admin.dto.BannerListDto;
import com.gofield.admin.dto.BrandDto;
import com.gofield.admin.dto.BrandListDto;
import com.gofield.admin.service.BannerService;
import com.gofield.admin.service.BrandService;
import com.gofield.domain.rds.domain.banner.Banner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/banner")
    public String getBannerListPage(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        BannerListDto result = bannerService.getBannerList(pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "banner/list";
    }

    @GetMapping("/banner/edit/{id}")
    public String getBannerEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        model.addAttribute("banner", bannerService.getBanner(id));
        return "banner/edit";
    }

    @GetMapping("/banner/delete/{id}")
    public String deleteBanner(@PathVariable Long id){
        bannerService.delete(id);
        return "redirect:/banner";
    }

    @PostMapping("/banner/edit")
    public String updateEditBanner(BannerDto bannerDto, @RequestParam(value = "image", required = false) MultipartFile image){
        bannerService.updateBanner(image, bannerDto);
        return "redirect:/banner";
    }

    @GetMapping("/banner/add")
    public String getBannerAddPage(Model model){
        model.addAttribute("banner", new BannerDto());
        return "banner/add";
    }

    @PostMapping("/banner/add")
    public String addBanner(BannerDto bannerDto, @RequestParam(value = "image") MultipartFile image){
        bannerService.save(image, bannerDto);
        return "redirect:/banner";
    }

}
