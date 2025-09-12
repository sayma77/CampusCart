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
            @RequestParam("category") String category_name,
            @RequestParam("image") String image,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        Category category = categoryRepository.findByName(category_name).get();
        User user = userRepository.findByEmail(principal.getName()).get();

        Product product = new Product(null, name, price, status, image, sellOrRent, "pending", user.getId(), category.getId(),
                null, null);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("success", "Product Added!");

        return "redirect:/marketplace";
    }
}
