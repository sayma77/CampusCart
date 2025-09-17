package com.noobs.CampusCart.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.UserRepository;
import com.noobs.CampusCart.service.CartService;
import com.noobs.CampusCart.service.NotificationService;
import com.noobs.CampusCart.service.WishlistService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private NotificationService notificationService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, Principal principal, HttpServletRequest request) {
        String path = request.getRequestURI(); // current page path

        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Cart count (hide on /cart)
            model.addAttribute("cart_item_count", path.startsWith("/cart") ? 0 : cartService.getItemsInCart(user).size());

            // Wishlist count (hide on /wishlist)
            model.addAttribute("wishlist_count", path.startsWith("/wishlist") ? 0 : wishlistService.getItemsInWishlist(user).size());

            // Notifications count (hide on /notifications)
            long unread = path.startsWith("/notifications") ? 0 :
                    notificationService.getNotificationsForUser(user).stream().filter(n -> !n.isRead()).count();
            model.addAttribute("unreadCount", unread);

        } else {
            model.addAttribute("cart_item_count", 0);
            model.addAttribute("wishlist_count", 0);
            model.addAttribute("unreadCount", 0);
        }
    }
}

