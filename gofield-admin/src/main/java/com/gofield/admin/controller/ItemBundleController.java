package com.gofield.admin.controller;

import com.gofield.admin.dto.*;
import com.gofield.admin.service.CategoryService;
import com.gofield.admin.service.ItemBundleService;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
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
                                    @RequestParam(required = false) Long parentId,
                                    @RequestParam(required = false) Long childId,
                                    @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        ItemBundleListDto result = itemBundleService.getBundleList(keyword, parentId, childId, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("keyword", keyword);
        model.addAttribute("parentId", parentId);
        model.addAttribute("childId", childId);
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "bundle/list";
    }

    @GetMapping("/bundle/edit/{id}")
    public String getItemBundleEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        ItemBundleEditDto itemBundleEditDto = itemBundleService.getItemBundleImage(id);
        model.addAttribute("bundle", itemBundleEditDto.getItemBundleDto());
        model.addAttribute("images", itemBundleEditDto.getImages());

        return "bundle/edit";
    }

    @GetMapping("/bundle/delete/{id}")
    public String deleteItemBundle(@PathVariable Long id){
        itemBundleService.delete(id);
        return "redirect:/bundle";
    }

    @GetMapping("/bundle/{id}/image/delete/{imageId}")
    public String deleteItemBundleImage(@PathVariable Long id,
                                        @PathVariable Long imageId){
        itemBundleService.deleteImage(id, imageId);
        return "redirect:/bundle/edit/"+id;
    }


    @PostMapping("/bundle/edit")
    public String updateEditItemBundle(ItemBundleDto itemBundleDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
        itemBundleService.updateItemBundle(image, images, itemBundleDto);
        return "redirect:/bundle";
    }

    @GetMapping("/bundle/{id}/image/sort/{imageId}")
    public String updateItemBundleImageSort(@PathVariable Long id, @PathVariable Long imageId, @RequestParam String type){
        itemBundleService.updateItemBundleImageSort(id, imageId, type);
        return "redirect:/bundle/edit/"+id;
    }

    @PostMapping("/bundle/{id}/image/sort/{imageId}/decrease")
    public String updateImageSort(ItemBundleDto itemBundleDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
        itemBundleService.updateItemBundle(image, images, itemBundleDto);
        return "redirect:/bundle";
    }

    @GetMapping("/bundle/add")
    public String getItemBundleAddPage(Model model){
        model.addAttribute("bundle", itemBundleService.getItemBundle(null));
        return "bundle/add";
    }

    @PostMapping("/bundle/add")
    public String addItemBundle(ItemBundleDto itemBundleDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
        itemBundleService.save(image, images, itemBundleDto);
        return "redirect:/bundle";
    }

}
