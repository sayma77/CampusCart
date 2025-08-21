package com.noobs.CampusCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noobs.CampusCart.model.Category;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.service.CategoryService;
import com.noobs.CampusCart.service.ProductService;

@Controller
public class MarketplaceController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/marketplace")
    public String showMarketplace(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "search", required = false) String search,
            Model model) {

        List<Product> products;

        // Search has highest priority
        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchProductsByName(search.trim());
        }
        // Combined category and type filter
        else if (categoryId != null && type != null && !type.equals("all")) {
            products = productService.getProductsByCategoryAndType(categoryId, type);
        }
        // Category filter only
        else if (categoryId != null) {
            products = productService.getProductsByCategory(categoryId);
        }
        // Type filter only
        else if (type != null && !type.equals("all")) {
            products = productService.getProductsBySellOrRent(type);
        }
        // No filters - show all products
        else {
            products = productService.getAllProducts();
        }

        // Get all categories for filter options
        List<Category> categories = categoryService.getAllCategories();

        // Add model attributes
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("selectedType", type);
        model.addAttribute("search", search);

        return "marketplace";
    }
}