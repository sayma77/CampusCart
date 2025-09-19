package com.noobs.CampusCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.noobs.CampusCart.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;




@Controller
public class HomePageController {
    @Autowired
    private ReviewRepository reviewRepository;
    @GetMapping("/")
    public String home(Model model) {
        // model.addAttribute("platformName", "CampusCart");
        // model.addAttribute("user", new User("Sayma"));
        // model.addAttribute("items", List.of(
        // new Item("Textbook", 250),
        // new Item("Lamp", 500)
        // ));
        model.addAttribute("reviews", reviewRepository.findAll());
        return "home";
    }
}
