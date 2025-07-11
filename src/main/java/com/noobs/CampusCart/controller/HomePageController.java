package com.noobs.CampusCart.controller;

import com.noobs.CampusCart.model.Item;
import com.noobs.CampusCart.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("platformName", "CampusCart");
        model.addAttribute("user", new User("Sayma"));
        model.addAttribute("items", List.of(
            new Item("Textbook", 250),
            new Item("Lamp", 500)
        ));
        return "home"; // refers to home.html in templates/
    }
}
