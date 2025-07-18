package com.noobs.CampusCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninPageController {
    @GetMapping("/signin")
    public String signin(Model model) {
        return "signin";
    }
}