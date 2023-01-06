package com.gofield.admin.controller;

import com.gofield.admin.dto.QnaDto;
import com.gofield.admin.dto.QnaListDto;
import com.gofield.admin.service.QnaService;
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
public class QnaController {

    private final QnaService qnaService;

    @GetMapping("/qna")
    public String getQnaListPage(@RequestParam(required = false) String keyword,
                                 @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, Model model, HttpSession session, Principal principal) {
        QnaListDto result = qnaService.getQnaList(pageable);
        session.setAttribute("username", principal.getName());
        model.addAttribute("list", result.getList());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", result.getPage().getNumber() + 1);
        model.addAttribute("totalItems", result.getPage().getTotalElements());
        model.addAttribute("totalPages", result.getPage().getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        return "qna/list";
    }

    @GetMapping("/qna/edit/{id}")
    public String getQnaEditPage(@PathVariable Long id, HttpSession session, Model model, Principal principal){
        session.setAttribute("username", principal.getName());
        model.addAttribute("qna", qnaService.getQna(id));
        return "qna/edit";
    }

    @GetMapping("/qna/delete/{id}")
    public String deleteBrand(@PathVariable Long id){
        qnaService.delete(id);
        return "redirect:/qna";
    }

    @PostMapping("/qna/edit")
    public String updateEditQna(QnaDto qnaDto){
        qnaService.updateQna(qnaDto);
        return "redirect:/qna";
    }
}
