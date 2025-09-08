package com.noobs.CampusCart.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.noobs.CampusCart.model.Category;

@Controller
public class AdminController {

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

        // dummy data for users

        // --- Dummy Users List ---
        List<Map<String, Object>> users = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        users.add(createUser(1L, "John Doe", "john@example.com", "ADMIN", "ACTIVE", LocalDate.of(2024, 1, 15)));
        users.add(createUser(2L, "Alice Smith", "alice@example.com", "SELLER", "ACTIVE", LocalDate.of(2024, 2, 10)));
        users.add(createUser(3L, "Bob Johnson", "bob@example.com", "BUYER", "BLOCKED", LocalDate.of(2024, 3, 5)));
        users.add(createUser(4L, "Charlie Lee", "charlie@example.com", "SELLER", "ACTIVE", LocalDate.of(2024, 4, 12)));
        users.add(createUser(5L, "Diana Prince", "diana@example.com", "BUYER", "ACTIVE", LocalDate.of(2024, 5, 20)));
        users.add(createUser(6L, "Eve Adams", "eve@example.com", "ADMIN", "BLOCKED", LocalDate.of(2024, 6, 8)));
        users.add(createUser(7L, "Frank Wright", "frank@example.com", "BUYER", "ACTIVE", LocalDate.of(2024, 7, 1)));
        users.add(createUser(8L, "Grace Hall", "grace@example.com", "SELLER", "ACTIVE", LocalDate.of(2024, 8, 18)));

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

        // dummy data for categories

        List<Category> dummyCategories = List.of(
                new Category(1L, "Electronics", "Gadgets and electronic devices"),
                new Category(2L, "Fashion", "Clothing, shoes, and accessories"),
                new Category(3L, "Home & Kitchen", "Furniture, appliances, and more"),
                new Category(4L, "Books", "Educational and leisure reading"),
                new Category(5L, "Sports", "Equipment and apparel for sports"));

        model.addAttribute("categories", dummyCategories);
        model.addAttribute("pageTitle", "Category Management");
        model.addAttribute("activeTab", "categories");

        return "admin/admin-categories";
    }

    // helper methods

    private Map<String, Object> createUser(Long id, String name, String email, String role, String status,
            LocalDate joinedDate) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("name", name);
        user.put("email", email);
        user.put("role", role);
        user.put("status", status);
        user.put("joinedDate", joinedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        user.put("initials", getInitials(name));
        return user;
    }

    private String getInitials(String name) {
        String[] parts = name.split(" ");
        String initials = "";
        for (String part : parts) {
            if (!part.isEmpty())
                initials += part.charAt(0);
        }
        return initials.toUpperCase();
    }

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
