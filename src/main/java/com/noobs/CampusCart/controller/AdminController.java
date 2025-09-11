package com.noobs.CampusCart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noobs.CampusCart.model.Category;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CategoryRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Dashboard
    @GetMapping("/admin/dashboard")
    public String dashboardPage(Model model) {

        // dummy data for dashboard

        // --- Stats Overview ---
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", 1234);
        stats.put("newUsersThisWeek", 12);
        stats.put("pendingProducts", 23);
        stats.put("activeOrders", 89);
        stats.put("ordersToday", 5);
        stats.put("monthlyRevenue", 12345.67);
        stats.put("revenueGrowth", "8.2%");

        model.addAttribute("stats", stats);
        return "admin/admin-dashboard"; // updated file name
    }

    // Users Management
    @GetMapping("/admin/users")
    public String usersPage(Model model) {

        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> !"ADMIN".equalsIgnoreCase(user.getRole()))
                .toList();
        model.addAttribute("users", users);

        // --- Pagination Dummy Data ---
        int totalUsers = users.size();
        int currentPage = 1;
        int totalPages = 1;
        int startIndex = 1;
        int endIndex = users.size();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("endIndex", endIndex);

        return "admin/admin-users";
    }

    @PostMapping("/admin/users/delete")
    public String deleteUser(@RequestParam Long id) {
        // Skip deleting admins for safety
        User user = userRepository.findById(id).orElse(null);
        if (user != null && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            userRepository.deleteById(id);
        }
        // Redirect back to users page after deletion
        return "redirect:/admin/users";
    }

    // Products Management
    @GetMapping("/admin/products")
    public String productsPage(Model model) {

        // dummy data for products

        // Dummy categories
        List<Map<String, Object>> categories = new ArrayList<>();
        categories.add(createCategory(1L, "Electronics", "fa-tv"));
        categories.add(createCategory(2L, "Books", "fa-book"));
        categories.add(createCategory(3L, "Clothing", "fa-shirt"));
        model.addAttribute("categories", categories);

        // Dummy sellers
        List<Map<String, Object>> sellers = new ArrayList<>();
        sellers.add(createSeller(1L, "John's Electronics"));
        sellers.add(createSeller(2L, "Alice's Books"));
        sellers.add(createSeller(3L, "Fashion Hub"));
        model.addAttribute("sellers", sellers);

        // Dummy products
        List<Map<String, Object>> products = new ArrayList<>();
        products.add(createProduct(1L, "MacBook Pro 13\"", 999.99, "APPROVED", "Electronics", "John's Electronics",
                "", "High performance laptop"));
        products.add(createProduct(2L, "The Great Gatsby", 19.99, "PENDING", "Books", "Alice's Books",
                "", "Classic novel"));
        products.add(createProduct(3L, "Men's T-Shirt", 29.99, "REJECTED", "Clothing", "Fashion Hub",
                "", "Cotton t-shirt"));
        products.add(createProduct(4L, "iPhone 14", 1199.99, "APPROVED", "Electronics", "John's Electronics",
                "", "Latest Apple smartphone"));
        model.addAttribute("products", products);

        return "admin/admin-products";
    }

    // Categories Management
    @GetMapping("/admin/categories")
    public String categoriesPage(Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Category Management");
        model.addAttribute("activeTab", "categories");

        return "admin/admin-categories";
    }

    @PostMapping("/admin/categories/add")
    public String addCategory(@RequestParam String name,
            @RequestParam String description,
            RedirectAttributes redirectAttributes) {
        if (name != null && !name.isEmpty()) {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryRepository.save(category);

            redirectAttributes.addFlashAttribute("success", "Category added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Category name is required!");
        }

        return "redirect:/admin/categories"; // redirect back to categories page
    }

    // helper methods

    private Map<String, Object> createCategory(Long id, String name, String icon) {
        Map<String, Object> category = new HashMap<>();
        category.put("id", id);
        category.put("name", name);
        category.put("icon", icon);
        return category;
    }

    private Map<String, Object> createSeller(Long id, String name) {
        Map<String, Object> seller = new HashMap<>();
        seller.put("id", id);
        seller.put("name", name);
        return seller;
    }

    private Map<String, Object> createProduct(Long id, String name, double price, String status, String categoryName,
            String sellerName, String imageUrl, String description) {
        Map<String, Object> product = new HashMap<>();
        product.put("id", id);
        product.put("name", name);
        product.put("price", price);
        product.put("status", status);
        product.put("categoryName", categoryName);
        product.put("sellerName", sellerName);
        product.put("imageUrl", imageUrl);
        product.put("description", description);
        return product;
    }

}
