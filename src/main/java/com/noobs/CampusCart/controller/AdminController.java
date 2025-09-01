package com.noobs.CampusCart.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

        // --- Recent Activities ---
        List<Map<String, String>> recentActivities = new ArrayList<>();

        recentActivities.add(Map.of(
                "description", "New user registered: alice@example.com",
                "timeAgo", "5 minutes ago",
                "statusColor", "bg-green-400"));
        recentActivities.add(Map.of(
                "description", "New order placed by john@example.com",
                "timeAgo", "20 minutes ago",
                "statusColor", "bg-blue-400"));
        recentActivities.add(Map.of(
                "description", "Product 'Wireless Mouse' pending approval",
                "timeAgo", "1 hour ago",
                "statusColor", "bg-amber-400"));
        recentActivities.add(Map.of(
                "description", "Category 'Smartphones' added",
                "timeAgo", "2 hours ago",
                "statusColor", "bg-purple-400"));

        model.addAttribute("recentActivities", recentActivities);

        // --- Top Categories ---
        List<Map<String, Object>> topCategories = new ArrayList<>();

        topCategories.add(Map.of("name", "Electronics", "productCount", 145, "icon", "fa-tv"));
        topCategories.add(Map.of("name", "Books", "productCount", 98, "icon", "fa-book"));
        topCategories.add(Map.of("name", "Fashion", "productCount", 120, "icon", "fa-tshirt"));
        topCategories.add(Map.of("name", "Home & Kitchen", "productCount", 75, "icon", "fa-blender"));

        model.addAttribute("topCategories", topCategories);

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

    // Orders Management
    @GetMapping("/admin/orders")
    public String ordersPage(Model model) {

        // dummy data for orders

        // Dummy customers
        Map<String, Object> customer1 = createCustomer("John Doe", "john@example.com");
        Map<String, Object> customer2 = createCustomer("Alice Smith", "alice@example.com");
        Map<String, Object> customer3 = createCustomer("Bob Johnson", "bob@example.com");

        // Dummy orders
        List<Map<String, Object>> orders = new ArrayList<>();

        orders.add(createOrder(101L, customer1, "PENDING", LocalDate.now().minusDays(2),
                Arrays.asList(
                        createOrderItem("MacBook Pro", 1, 999.0, ""),
                        createOrderItem("iPhone 14", 2, 1200.0, "")),
                3399.0));

        orders.add(createOrder(102L, customer2, "SHIPPED", LocalDate.now().minusDays(5),
                Arrays.asList(
                        createOrderItem("Samsung Galaxy S23", 1, 799.0, "")),
                799.0));

        orders.add(createOrder(103L, customer3, "DELIVERED", LocalDate.now().minusDays(10),
                Arrays.asList(
                        createOrderItem("Sony Headphones", 1, 199.0, ""),
                        createOrderItem("Logitech Mouse", 2, 49.0, ""),
                        createOrderItem("Dell Monitor", 1, 299.0, "")),
                596.0));

        // Dummy order counts for tabs
        Map<String, Integer> orderCounts = new HashMap<>();
        orderCounts.put("total", orders.size());
        orderCounts.put("pending", 1);
        orderCounts.put("shipped", 1);
        orderCounts.put("delivered", 1);

        model.addAttribute("orders", orders);
        model.addAttribute("orderCounts", orderCounts);

        return "admin/admin-orders";
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

    // Analytics
    @GetMapping("/admin/analytics")
    public String analyticsPage(Model model) {

        // dummy data for analytics

        // Key metrics
        Map<String, Object> analytics = Map.of(
                "totalRevenue", 45231,
                "revenueGrowth", 12.5,
                "totalOrders", 324,
                "ordersGrowth", 8.2,
                "activeUsers", 1234,
                "usersGrowth", 15.3,
                "totalProducts", 567,
                "productsGrowth", 6.7);

        // Top products
        List<Map<String, Object>> topProducts = List.of(
                Map.of("name", "MacBook Pro 13\"", "categoryName", "Electronics", "salesCount", 45, "revenue", 45000),
                Map.of("name", "Nike Air Max", "categoryName", "Fashion", "salesCount", 32, "revenue", 9600),
                Map.of("name", "Instant Pot", "categoryName", "Home & Kitchen", "salesCount", 28, "revenue", 2800),
                Map.of("name", "The Alchemist", "categoryName", "Books", "salesCount", 20, "revenue", 400),
                Map.of("name", "Football", "categoryName", "Sports", "salesCount", 18, "revenue", 900));

        // Top sellers
        List<Map<String, Object>> topSellers = List.of(
                Map.of("name", "John's Store", "email", "john@example.com", "initials", "JS", "ordersCount", 23,
                        "revenue", 12450),
                Map.of("name", "Anna's Boutique", "email", "anna@example.com", "initials", "AB", "ordersCount", 17,
                        "revenue", 8900),
                Map.of("name", "TechWorld", "email", "tech@example.com", "initials", "TW", "ordersCount", 12, "revenue",
                        15300));

        // Chart data
        // List<Integer> revenueChartData = List.of(5000, 7200, 8100, 6500, 9100, 10200,
        // 11200); // last 7 days
        // List<String> revenueChartLabels = List.of("Mon", "Tue", "Wed", "Thu", "Fri",
        // "Sat", "Sun");

        // List<Integer> ordersChartData = List.of(20, 32, 28, 25, 40, 35, 45); // last
        // 7 days
        // List<String> ordersChartLabels = List.of("Mon", "Tue", "Wed", "Thu", "Fri",
        // "Sat", "Sun");

        model.addAttribute("analytics", analytics);
        model.addAttribute("topProducts", topProducts);
        model.addAttribute("topSellers", topSellers);
        // model.addAttribute("revenueChartData", revenueChartData);
        // model.addAttribute("revenueChartLabels", revenueChartLabels);
        // model.addAttribute("ordersChartData", ordersChartData);
        // model.addAttribute("ordersChartLabels", ordersChartLabels);

        model.addAttribute("pageTitle", "Analytics");
        model.addAttribute("activeTab", "analytics");

        return "admin/admin-analytics";
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

    private Map<String, Object> createCustomer(String name, String email) {
        Map<String, Object> customer = new HashMap<>();
        customer.put("name", name);
        customer.put("email", email);
        customer.put("initials", getInitials(name));
        return customer;
    }

    private Map<String, Object> createOrder(Long id, Map<String, Object> customer, String status, LocalDate date,
            List<Map<String, Object>> items, Double total) {
        Map<String, Object> order = new HashMap<>();
        order.put("id", id);
        order.put("customer", customer);
        order.put("status", status);
        order.put("orderDate", date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        order.put("items", items);
        order.put("total", total);
        return order;
    }

    private Map<String, Object> createOrderItem(String productName, int quantity, double price, String image) {
        Map<String, Object> item = new HashMap<>();
        item.put("productName", productName);
        item.put("quantity", quantity);
        item.put("price", price);
        item.put("productImage", image);
        return item;
    }

}
