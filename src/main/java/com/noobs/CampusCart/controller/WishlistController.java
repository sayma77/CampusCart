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
import com.noobs.CampusCart.model.WishlistItem;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.repository.UserRepository;
import com.noobs.CampusCart.repository.WishlistItemRepository;
import com.noobs.CampusCart.repository.CartItemRepository;

@Controller
public class WishlistController {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private WishlistItemRepository wishlistRepository;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private CartItemRepository cartItemRepository;

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
                        Principal principal) {
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
                        Principal principal) {
                User user = userRepository.findByEmail(principal.getName())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                wishlistRepository.findByUserAndProduct(user, product)
                                .ifPresent(wishlistRepository::delete);

                return "redirect:/wishlist";
        }

        // Add single wishlist item to cart
        @PostMapping("/wishlist/add-to-cart")
        public String addToCartFromWishlist(
                        @RequestParam("productId") Long productId,
                        Principal principal) {
                User user = userRepository.findByEmail(principal.getName())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                // Add to cart

                CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product).orElse(null);
                if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + 1);
                        cartItemRepository.save(existingItem);
                } else {
                        CartItem newItem = new CartItem(null, 1, LocalDateTime.now(), product, user);
                        cartItemRepository.save(newItem);
                }

                // Remove from wishlist after adding to cart
                wishlistRepository.findByUserAndProduct(user, product)
                                .ifPresent(wishlistRepository::delete);

                return "redirect:/wishlist";
        }

        // Add all wishlist items to cart
        @PostMapping("/wishlist/add-all-to-cart")
        public String addAllToCartFromWishlist(Principal principal) {
                User user = userRepository.findByEmail(principal.getName())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                List<WishlistItem> wishlistItems = wishlistRepository.findByUser(user);

                for (WishlistItem item : wishlistItems) {
                        Product product = productRepository.findById(item.getProduct().getId())
                                        .orElseThrow(() -> new RuntimeException("Product not found"));

                        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product).orElse(null);
                        if (existingItem != null) {
                                existingItem.setQuantity(existingItem.getQuantity() + 1);
                                cartItemRepository.save(existingItem);
                        } else {
                                CartItem newItem = new CartItem(null, 1, LocalDateTime.now(), product, user);
                                cartItemRepository.save(newItem);
                        }
                }

                // Clear wishlist
                wishlistRepository.deleteAll(wishlistItems);

                return "redirect:/wishlist";
        }

}
