package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.noobs.CampusCart.utils.AppLogger;

@Controller
public class CartPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        double subtotal = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("currency", "৳"); // optional helper
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            Principal principal
    ) {

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

}
