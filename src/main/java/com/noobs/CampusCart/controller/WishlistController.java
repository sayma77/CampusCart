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

import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.model.WishlistItem;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.UserRepository;
import com.noobs.CampusCart.repository.WishlistItemRepository;

@Controller
public class WishlistController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistItemRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    // Show wishlist page
    @GetMapping("/wishlist")
    public String viewWishlist(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        List<WishlistItem> wishlistItems = wishlistRepository.findByUser(user);

        model.addAttribute("wishlistItems", wishlistItems);
        return "wishlist"; // wishlist.html
    }

    // Add product to wishlist
    @PostMapping("/wishlist")
    public String addToWishlist(
            @RequestParam("productId") Long productId,
            Principal principal
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if already in wishlist
        WishlistItem existingItem = wishlistRepository.findByUserAndProduct(user, product).orElse(null);
        if (existingItem == null) {
            WishlistItem newItem = new WishlistItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setAddedDate(LocalDateTime.now());
            wishlistRepository.save(newItem);
        }
        return "redirect:/marketplace";
    }

    // Remove product from wishlist
    @PostMapping("/wishlist/remove")
    public String removeFromWishlist(
            @RequestParam("productId") Long productId,
            Principal principal
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        wishlistRepository.findByUserAndProduct(user, product)
                .ifPresent(wishlistRepository::delete);

        return "redirect:/wishlist";
    }
}
