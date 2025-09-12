package com.noobs.CampusCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/403")
    public String forbidden() {
        return "error/403"; // Thymeleaf template: src/main/resources/templates/error/403.html
    }

    @GetMapping("/404")
    public String notFound() {
        return "error/404"; // Thymeleaf template: src/main/resources/templates/error/404.html
    }
}
