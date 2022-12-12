package com.gofield.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RootController {

    @GetMapping("/index")
    public String root(){
        return "index";
    }
}
