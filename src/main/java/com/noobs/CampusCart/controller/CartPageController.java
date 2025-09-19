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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            @RequestParam("quantity") int requestedQuantity,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product).orElse(null);
        if (existingItem == null) {
            redirectAttributes.addFlashAttribute("error", "Item not found in cart.");
            return "redirect:/cart";
        }

        // If user entered non-positive quantity -> remove item
        if (requestedQuantity <= 0) {
            cartItemRepository.delete(existingItem);
            redirectAttributes.addFlashAttribute("info", "Item removed from cart.");
            return "redirect:/cart";
        }

        // If requested more than available, clamp and show message
        if (requestedQuantity > product.getQuantity()) {
            int available = product.getQuantity();
            existingItem.setQuantity(available); // clamp to available
            cartItemRepository.save(existingItem);
            redirectAttributes.addFlashAttribute("warningMessage",
                    "⚠ You requested more than available for \"" + product.getName() + "\". Quantity adjusted to "
                            + available + ".");
            return "redirect:/cart";
        }

        // Valid quantity -> save
        existingItem.setQuantity(requestedQuantity);
        cartItemRepository.save(existingItem);
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
