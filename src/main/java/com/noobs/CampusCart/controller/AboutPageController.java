package com.noobs.CampusCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutPageController {

    @GetMapping("/")
    public String homePage() {
        return "home"; // This will load home.html
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // This will load about.html
    }
}
