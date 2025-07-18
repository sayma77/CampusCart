package com.noobs.CampusCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupPageController {
    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }
}