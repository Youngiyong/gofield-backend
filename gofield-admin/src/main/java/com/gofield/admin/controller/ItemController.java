package com.gofield.admin.controller;

import com.gofield.admin.dto.ItemDto;
import com.gofield.admin.dto.ItemListDto;
import com.gofield.admin.service.ItemService;
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
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item")
    public String getItemListPage(@RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) EItemStatusFlag status,
                                  @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        ItemListDto result = itemService.getItemList(keyword, status, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);
        model.addAttribute("allCount", result.getAllCount());
        model.addAttribute("salesCount", result.getSalesCount());
        model.addAttribute("hideCount", result.getHideCount());
        model.addAttribute("soldOutCount", result.getSoldOutCount());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "item/list";
    }

    @GetMapping("/item/used/edit/{id}")
    public String getItemUsedEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        ItemDto itemDto = itemService.getUsedItem(id);
        model.addAttribute("item", itemDto);
//        model.addAttribute("images", itemBundleEditDto.getImages());
        return "item/used_edit";
    }
//
    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable Long id){
        itemService.delete(id);
        return "redirect:/item";
    }
//
//    @GetMapping("/bundle/{id}/image/delete/{imageId}")
//    public String deleteItemBundleImage(@PathVariable Long id,
//                                        @PathVariable Long imageId){
//        itemBundleService.deleteImage(id, imageId);
//        return "redirect:/bundle/edit/"+id;
//    }
//
//
//    @PostMapping("/item/add/used")
//    public String updateEditItemBundle(ItemDto itemDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
//        itemService.updateItemBundle(image, images, itemDto);
//        return "redirect:/item";
//    }


    @GetMapping("/item/add/new")
    public String getItemNewAddPage(Model model){
        model.addAttribute("item",  itemService.getItem(null));
        return "item/new_add";
    }

    @GetMapping("/item/add/used")
    public String getItemUsedAddPage(Model model){
        model.addAttribute("item", itemService.getItem(null));
        return "item/used_add";
    }

    @PostMapping("/item/add/new")
    public String addItemNew(ItemDto itemDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
        itemService.saveNewItem(image, images, itemDto);
        return "redirect:/item";
    }

    @PostMapping("/item/add/used")
    public String addItemUsed(ItemDto itemDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
        itemService.saveUsedItem(image, images, itemDto);
        return "redirect:/item";
    }

}
