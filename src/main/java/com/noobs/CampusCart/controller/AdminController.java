package com.noobs.CampusCart.controller;

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
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Dashboard
    @GetMapping("/admin/dashboard")
    public String dashboardPage(Model model) {

        long totalUsers = userRepository.count();
        model.addAttribute("totalUsers", totalUsers);
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
    public String productsPage(@RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "categoryId", required = false) Long categoryId,
        @RequestParam(name = "status", required = false) String status,Model model) {

        List<Product> products = productRepository.findAll();
        if (search != null && !search.isEmpty()) {
            // Search has priority
            products = productRepository.findByNameContainingWithUserAndCategory(search);
        } else if (categoryId != null && status != null && !status.isEmpty()) {
            // Filter by category and status
            products = productRepository.findByCategoryAndValidity(categoryId, status.toLowerCase());
        } else if (categoryId != null) {
            // Only category filter
            products = productRepository.findByCategoryId(categoryId);
        } else if (status != null && !status.isEmpty()) {
            // Only status filter
            products = productRepository.findByValidity(status.toLowerCase());
        } else {
            // No filters → show all
            products = productRepository.findAllWithUserAndCategory();
        }
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("search", search);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("selectedStatus", status);
        return "admin/admin-products";
    }

    @PostMapping("/admin/products/approve")
    public String approveProduct(@RequestParam("productId") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setValidity("approved");
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/reject")
    public String rejectProduct(@RequestParam("productId") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setValidity("rejected");
        productRepository.save(product);
        return "redirect:/admin/products";
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

}
