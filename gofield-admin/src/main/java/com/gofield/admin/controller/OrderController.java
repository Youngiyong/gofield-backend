package com.gofield.admin.controller;

import com.gofield.admin.dto.OrderCancelListDto;
import com.gofield.admin.dto.OrderShippingListDto;
import com.gofield.admin.dto.OrderShippingDto;
import com.gofield.admin.service.OrderService;
import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
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
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public String getOrderListPage(@RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) EOrderShippingStatusFlag status,
                                   @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        OrderShippingListDto result = orderService.getOrderList(keyword, status, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);
        model.addAttribute("allCount", result.getAllCount());
        model.addAttribute("receiptCount", result.getReceiptCount());
        model.addAttribute("readyCount", result.getReadyCount());
        model.addAttribute("deliveryCount", result.getDeliveryCount());
        model.addAttribute("deliveryCompleteCount", result.getDeliveryCompleteCount());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "order/list";
    }

    @GetMapping("/cancel")
    public String getOrderCancelListPage(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) EOrderCancelStatusFlag status,
                                         @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {

        OrderCancelListDto result = orderService.getOrderCancelList(keyword, status, pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);
        model.addAttribute("allCount", result.getAllCount());
        model.addAttribute("receiptCount", result.getReceiptCount());
        model.addAttribute("refuseCount", result.getRefuseCount());
        model.addAttribute("completeCount", result.getCompleteCount());
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "cancel/list";
    }

    @GetMapping("/order/shipping/edit/{id}")
    public String getItemBundleEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        OrderShippingDto projection = orderService.getOrderShipping(id);
        model.addAttribute("order", projection);
        model.addAttribute("codeList", projection.getCodeList());
        return "order/edit";
    }

    @PostMapping("/order/shipping/edit")
    public String updateEditOrderShipping(OrderShippingDto request){
        orderService.updateOrderShipping(request);
        return "redirect:/order";
    }

//    @GetMapping("/bundle/delete/{id}")
//    public String deleteItemBundle(@PathVariable Long id){
//        itemBundleService.delete(id);
//        return "redirect:/bundle";
//    }
//
//    @GetMapping("/bundle/{id}/image/delete/{imageId}")
//    public String deleteItemBundleImage(@PathVariable Long id,
//                                        @PathVariable Long imageId){
//        itemBundleService.deleteImage(id, imageId);
//        return "redirect:/bundle/edit/"+id;
//    }
//
//

//
//    @GetMapping("/bundle/add")
//    public String getItemBundleAddPage(Model model){
//        model.addAttribute("bundle", itemBundleService.getItemBundle(null));
//        return "bundle/add";
//    }
//
//    @PostMapping("/bundle/add")
//    public String addItemBundle(ItemBundleDto itemBundleDto, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam(value = "images", required = false) List<MultipartFile> images){
//        itemBundleService.save(image, images, itemBundleDto);
//        return "redirect:/bundle";
//    }

}
