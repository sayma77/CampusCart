package com.noobs.CampusCart.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/contactus")
    public String contactUsPage(Model model) {
        return "contactus"; // looks for contactus.html in templates folder
    }
}
