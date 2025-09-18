package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noobs.CampusCart.model.Category;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CategoryRepository;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Controller
public class SellController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Show the Sell Form
    @GetMapping("/sell")
    public String showSellForm(Model model) {
        List<Category> all_cat = categoryRepository.findAll();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", all_cat);
        return "sell";
    }

    @PostMapping("/sell")
public String postItem(
        @RequestParam("name") String name,
        @RequestParam("price") Double price,
        @RequestParam("status") String status,
        @RequestParam("sellOrRent") String sellOrRent,
        @RequestParam("categoryId") Long categoryId,
        @RequestParam("image") String image,
        @RequestParam("quantity") int quantity,
        Principal principal,
        RedirectAttributes redirectAttributes) {

    // Get category safely
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

    // Get user safely
    User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getName()));

    // Create product
    Product product = new Product(name, price, status, image, sellOrRent, "pending",
            user, category, quantity, 0);

    productRepository.save(product);

    redirectAttributes.addFlashAttribute("success", "Product Added!");
    return "redirect:/marketplace";
}


}
