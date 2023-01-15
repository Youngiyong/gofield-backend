package com.gofield.sns.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RootController {

    @GetMapping("/")
    public String health(){
        return "OK";
    }
}
