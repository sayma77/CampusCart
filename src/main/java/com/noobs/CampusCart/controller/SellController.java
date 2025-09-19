package com.noobs.CampusCart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
        @RequestParam("imageFile") MultipartFile imageFile,  // ✅ accept file instead of String
        @RequestParam("quantity") int quantity,
        Principal principal,
        RedirectAttributes redirectAttributes) {

    // Get category
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

    // Get user
    User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + principal.getName()));

    // Save uploaded image to /uploads folder
    String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
    Path uploadPath = Paths.get("uploads");
    try {
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(imageFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        throw new RuntimeException("Image upload failed", e);
    }

    // Create product with image path
    String imagePath = "/uploads/" + fileName;
    Product product = new Product(name, price, status, imagePath, sellOrRent, "pending",
        user, category, quantity, 0);

                productRepository.save(product);

                redirectAttributes.addFlashAttribute("success", "Product Added!");
                return "redirect:/marketplace";
        }


}
