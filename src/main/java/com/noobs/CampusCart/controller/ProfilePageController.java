package com.noobs.CampusCart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noobs.CampusCart.model.Category;
import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.OrderItem;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CategoryRepository;
import com.noobs.CampusCart.repository.OrderRepository;
import com.noobs.CampusCart.repository.OrderItemRepository;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.service.UserService;

@Controller
public class ProfilePageController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final UserService userService;

    public ProfilePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profilePage(
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            Model model,
            Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        Map<String, String> user_data = new HashMap<>();
        user_data.put("name", user.getName());
        user_data.put("username", user.getUsername());
        user_data.put("email", user.getEmail());
        user_data.put("hall", user.getHall());

        List<Order> orders = List.of();
        List<Product> products = List.of();

        switch (filter) {
            case "buy":
                orders = orderRepository.findByUser(user).stream()
                        .map(order -> {
                            order.setOrderItems(
                                    order.getOrderItems().stream()
                                            .filter(oi -> oi.getProduct().getSellOrRent().equalsIgnoreCase("sell"))
                                            .toList());
                            return order;
                        })
                        .filter(o -> !o.getOrderItems().isEmpty())
                        .toList();
                break;

            case "taken_rent":
                orders = orderRepository.findByUser(user).stream()
                        .map(order -> {
                            order.setOrderItems(
                                    order.getOrderItems().stream()
                                            .filter(oi -> oi.getProduct().getSellOrRent().equalsIgnoreCase("rent"))
                                            .toList());
                            return order;
                        })
                        .filter(o -> !o.getOrderItems().isEmpty())
                        .toList();
                break;

            case "sell":
                products = productRepository.findByTypeAndApproved("sell").stream()
                        .filter(p -> p.getUser().getId().equals(user.getId()))
                        .toList();
                break;

            case "given_rent":
                products = productRepository.findByTypeAndApproved("rent").stream()
                        .filter(p -> p.getUser().getId().equals(user.getId()))
                        .toList();
                break;
            case "orders_received":
                orders = orderRepository.findAll().stream()
                        .map(order -> {
                            order.setOrderItems(
                                    order.getOrderItems().stream()
                                            .filter(oi -> oi.getProduct().getUser().getId().equals(user.getId()))
                                            .toList()
                            );
                            return order;
                        })
                        .filter(o -> !o.getOrderItems().isEmpty())
                        .toList();
                break;

        }

        // Format dates for orders
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        for (Order order : orders) {
            order.setFormattedDate(order.getOrderDate().format(formatter));
        }

        model.addAttribute("currentUser", user);
        model.addAttribute("user", user_data);
        model.addAttribute("orders", orders);
        model.addAttribute("products", products);
        model.addAttribute("selectedFilter", filter);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("hall") String hall,
            @RequestParam(value = "password", required = false) String password,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        // Fetch existing user
        User user = userService.getUserByEmail(principal.getName());

        // Update only fields that are non-empty
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }
        // if (username != null && !username.isEmpty()) {
        // user.setUsername(username);
        // }
        if (hall != null && !hall.isEmpty()) {
            user.setHall(hall);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password); // will be encoded in service
        }

        // Save updates using service
        userService.updateProfile(principal.getName(), user);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/profile/updateProduct")
    public String updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("status") String status,
            @RequestParam("sellOrRent") String sellOrRent,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, // accept uploaded file
            @RequestParam("quantity") Integer quantity,
            RedirectAttributes redirectAttributes) {

        // Fetch existing product
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(name);
            existingProduct.setPrice(price);
            existingProduct.setStatus(status);
            existingProduct.setQuantity(quantity);
            existingProduct.setSellOrRent(sellOrRent);

            // Update category
            if (categoryId != null && categoryId != 0) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                existingProduct.setCategory(category);
            }

            // Handle new image upload if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");
                try {
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }
                    Files.copy(imageFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    existingProduct.setImage("/uploads/" + fileName); // store relative path for Thymeleaf
                } catch (IOException e) {
                    throw new RuntimeException("Image upload failed", e);
                }
            }

            productRepository.save(existingProduct);
            redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Product not found!");
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/rejectProduct")
    public String rejectProduct(@RequestParam("productId") Long productId,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        // Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        // Ensure only the owner can reject
        User currentUser = userService.getUserByEmail(principal.getName());
        if (!product.getUser().getId().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to reject this product!");
            return "redirect:/profile?filter=sell";
        }

        // Mark as rejected
        product.setValidity("rejected");
        productRepository.save(product);

        redirectAttributes.addFlashAttribute("success", "Product rejected successfully!");
        return "redirect:/profile?filter=sell";
    }

    @PostMapping("/profile/updateOrderStatus")
    public String updateOrderStatus(
            @RequestParam("orderItemId") Long orderItemId,
            @RequestParam(value = "selectedFilter", required = false) String selectedFilter,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Order Item ID"));

        User currentUser = userService.getUserByEmail(principal.getName());
        String currentStatus = orderItem.getStatus();

        // Seller can change pending → shipped
        if (currentStatus.equalsIgnoreCase("PENDING")
                && orderItem.getProduct().getUser().getId().equals(currentUser.getId())) {
            orderItem.setStatus("SHIPPED");
        } // Buyer can change shipped → received
        else if (currentStatus.equalsIgnoreCase("SHIPPED")
                && orderItem.getOrder().getUser().getId().equals(currentUser.getId())) {
            orderItem.setStatus("RECEIVED");
        } else {
            redirectAttributes.addFlashAttribute("error", "You are not authorized to change this status!");
            return "redirect:/profile" + (selectedFilter != null ? "?filter=" + selectedFilter : "");
        }

        orderItemRepository.save(orderItem);

        return "redirect:/profile" + (selectedFilter != null ? "?filter=" + selectedFilter : "");
    }

}
