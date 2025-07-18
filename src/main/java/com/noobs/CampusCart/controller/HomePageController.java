package com.noobs.CampusCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String home(Model model) {
        // model.addAttribute("platformName", "CampusCart");
        // model.addAttribute("user", new User("Sayma"));
        // model.addAttribute("items", List.of(
        //     new Item("Textbook", 250),
        //     new Item("Lamp", 500)
        // ));
        return "home";
    }
}
