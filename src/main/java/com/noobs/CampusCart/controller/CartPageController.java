package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noobs.CampusCart.model.CartItem;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CartItemRepository;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.UserRepository;

@Controller
public class CartPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal,
        @RequestParam(value = "sort", required = false) String sort) {
        User user = userRepository.findByEmail(principal.getName()).get();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        
        // Sorting
        if ("priceAsc".equals(sort)) {
            cartItems.sort((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()));
        } else if ("priceDesc".equals(sort)) {
            cartItems.sort((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()));
        }
        double subtotal = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("currency", "৳"); // optional helper
        model.addAttribute("sort", sort);
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            Principal principal) {

        // Get logged-in user
        User user = userRepository.findByEmail(principal.getName()).get();
        // Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // Check if already in cart
        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product).orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem(null, quantity, LocalDateTime.now(), product, user);
            cartItemRepository.save(newItem);
        }
        return "redirect:/marketplace";
    }
    @PostMapping("/cart/update")
    public String updateCartQuantity(
        @RequestParam("productId") Long productId,
        @RequestParam("quantity") int quantity,
        Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cartItemRepository.findByUserAndProduct(user, product).ifPresent(item -> {
            if (quantity > 0) {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            } else {
                // If user sets 0 or negative, remove item
                cartItemRepository.delete(item);
            }
        });
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromcart(
            @RequestParam("productId") Long productId,
            Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartItemRepository.findByUserAndProduct(user, product)
                .ifPresent(cartItemRepository::delete);

        return "redirect:/cart";
    }

}
