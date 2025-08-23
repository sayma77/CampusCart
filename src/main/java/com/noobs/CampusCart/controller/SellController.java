package com.noobs.CampusCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.UserRepository;
import com.noobs.CampusCart.service.CategoryService;
import com.noobs.CampusCart.service.ProductService;

@Controller
public class SellController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sell")
    public String showSellForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // <-- add this
        return "sell";
    }


    // Handle Form Submission
    @PostMapping("/sell")
    public String postItem(@ModelAttribute Product product) {

        // Get authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email is used as username in your security config

        // Fetch the User entity to get its ID
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            product.setUserId(user.getId());
        }

        // Save product
        productService.save(product);

        // Redirect to marketplace after posting
        return "redirect:/marketplace";
    }
}
