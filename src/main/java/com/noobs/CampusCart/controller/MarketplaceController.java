package com.noobs.CampusCart.controller;

import java.util.List; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.noobs.CampusCart.model.Product;

@Controller
public class MarketplaceController {

    @GetMapping("/marketplace")
    public String showMarketplace(Model model) {
        List<Product> products = List.of(
            new Product(1L, "iPhone 14", "Used for 3 months", 95000, "/images/iphone.jpg"),
            new Product(2L, "Chair", "Wooden study chair", 1200, "/images/chair.jpg")
        );

        model.addAttribute("products", products);
        return "marketplace";
    }
}
