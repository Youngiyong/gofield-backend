package com.gofield.admin.controller;

import com.gofield.common.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage(HttpServletRequest request, Model model){
        ErrorCode errorCode = ErrorCode.E500_INTERNAL_SERVER;
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorCode = ErrorCode.E404_NOT_FOUND_EXCEPTION;
            }
        }
        model.addAttribute("errorCode", errorCode);
        return "error";

    }
}
