package com.noobs.CampusCart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfilePageController {

    @GetMapping("/profile")
    public String profilePage(Model model) {
        // Create a sample user map (replace with actual user service)
        Map<String, String> user = new HashMap<>();
        user.put("username", "warniya siddique");
        user.put("email", "u2204026@student.cuet.ac.bd");
        user.put("location", "ShamshenNahar Hall");
        
        // Create sample orders list using maps (replace with actual order service)
        List<Map<String, String>> userOrders = new ArrayList<>();
        
        Map<String, String> order1 = new HashMap<>();
        order1.put("itemName", "MacBook Pro 2020");
        order1.put("description", "Excellent condition, rarely used");
        order1.put("type", "SELL");
        order1.put("price", "85000");
        order1.put("status", "ACTIVE");
        order1.put("createdAt", "2 days ago");
        userOrders.add(order1);
        
        Map<String, String> order2 = new HashMap<>();
        order2.put("itemName", "Engineering Mathematics Books");
        order2.put("description", "Complete set for CSE students");
        order2.put("type", "RENT");
        order2.put("price", "500");
        order2.put("status", "RENTED");
        order2.put("createdAt", "1 week ago");
        userOrders.add(order2);
        
         Map<String, String> order3= new HashMap<>();
        order3.put("itemName", "T-scale");
        order3.put("description", "Good condition");
        order3.put("type", "BUY");
        order3.put("price", "120");
        order3.put("status", "SOLD");
        order3.put("createdAt", "Just Now");
        userOrders.add(order3);

         Map<String, String> order4 = new HashMap<>();
        order4.put("itemName", "HAVIT HV-H2178D 3.5mm Wired Headphone");
        order4.put("description", "Good condition");
        order4.put("type", "BUY");
        order4.put("price", "600");
        order4.put("status", "SOLD");
        order4.put("createdAt", "2 days ago");
        userOrders.add(order4);

         Map<String, String> order5 = new HashMap<>();
        order5.put("itemName", "Set Squares");
        order5.put("description", "Must Have For  Engineering Drawing");
        order5.put("type", "BUY");
        order5.put("price", "300");
        order5.put("status", "SOLD");
        order5.put("createdAt", "5 days ago");
        userOrders.add(order5);
        
        
        
        
        model.addAttribute("user", user);
        model.addAttribute("userOrders", userOrders);
        
        return "profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String location,
                              @RequestParam String password,
                              Model model) {
        // Handle profile update logic here
        // This would typically involve saving to database
        
        // For now, just redirect back to profile page
        return "redirect:/profile";
    }
}
