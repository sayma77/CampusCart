package com.noobs.CampusCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.service.CategoryService;
import com.noobs.CampusCart.service.ProductService;
import com.noobs.CampusCart.service.UserService;

@Controller
public class SellController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService; // you'll need this to fetch user by email

    // Show the Sell Form
    @GetMapping("/sell")
    public String showSellForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // for dropdown
        return "sell";
    }

    // Handle Form Submission
    @PostMapping("/sell")
    public String postItem(@ModelAttribute Product product) {

        // Get currently logged-in user's email
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = null;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername(); // email is username in your setup
        }

        if (email != null) {
            User user = userService.getUserByEmail(email);
            product.setUserId(user.getId()); // set user id of the poster
        }

        productService.save(product); // save the product with userId

        return "redirect:/marketplace";
    }
}
