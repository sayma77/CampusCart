package com.noobs.CampusCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.noobs.CampusCart.repository.ReviewRepository;

@Controller
public class HomePageController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "home";
    }
}
