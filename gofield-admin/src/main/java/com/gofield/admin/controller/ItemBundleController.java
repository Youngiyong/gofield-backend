package com.gofield.admin.controller;

import com.gofield.admin.dto.CategoryDto;
import com.gofield.admin.dto.CategoryListDto;
import com.gofield.admin.dto.ItemBundleDto;
import com.gofield.admin.dto.ItemBundleListDto;
import com.gofield.admin.service.CategoryService;
import com.gofield.admin.service.ItemBundleService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemBundleController {

    private final ItemBundleService itemBundleService;

    @GetMapping("/bundle")
    public String getBundleListPage(@RequestParam(required = false) String keyword,
                                    @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        ItemBundleListDto result = itemBundleService.getBundleList(keyword, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "bundle/list";
    }

    @GetMapping("/bundle/edit/{id}")
    public String getItemBundleEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        model.addAttribute("bundle", itemBundleService.getItemBundle(id));
        return "bundle/edit";
    }

    @GetMapping("/bundle/delete/{id}")
    public String deleteItemBundle(@PathVariable Long id){
        itemBundleService.delete(id);
        return "redirect:/bundle";
    }

    @PostMapping("/bundle/edit")
    public String updateEditItemBundle(ItemBundleDto itemBundleDto){
        itemBundleService.updateItemBundle(itemBundleDto);
        return "redirect:/bundle";
    }

    @GetMapping("/bundle/add")
    public String getItemBundleAddPage(Model model){
        model.addAttribute("bundle", itemBundleService.getItemBundle(null));
        return "bundle/add";
    }

    @PostMapping("/bundle/add")
    public String addItemBundle(ItemBundleDto itemBundleDto, @RequestParam("image") MultipartFile image, @RequestParam("images") List<MultipartFile> images){
        itemBundleService.save(image, images, itemBundleDto);
        return "redirect:/bundle";
    }

}
