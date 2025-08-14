package com.noobs.CampusCart.controller;

import com.noobs.CampusCart.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SellController {

    // Show the Sell Form
    @GetMapping("/sell")
    public String showSellForm(Model model) {
        model.addAttribute("product", new Product(null, "", "", 0, ""));
        return "sell";
    }

    // Handle Form Submission
    @PostMapping("/sell")
    public String postItem(@ModelAttribute Product product) {
        // TODO: Save product to database or service
        System.out.println("New product posted: " + product);

        // Redirect to marketplace after posting
        return "redirect:/marketplace";
    }
}

