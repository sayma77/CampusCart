package com.noobs.CampusCart.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noobs.CampusCart.model.WishlistItem;
import com.noobs.CampusCart.model.Notification;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.WishlistItemRepository;

@Service
public class WishlistService {

    @Autowired
    private WishlistItemRepository wishlistRepo;

    // Add to wishlist
    public void addToWishlist(User user, Product product) {
        WishlistItem item = wishlistRepo.findByUserAndProduct(user, product).orElse(null);

        if (item == null) {
            item = new WishlistItem();
            item.setUser(user);
            item.setProduct(product);
            item.setAddedDate(LocalDateTime.now());
            wishlistRepo.save(item);
        }
        // else do nothing (already in wishlist)
    }

    // Remove from wishlist
    public void removeFromWishlist(User user, Product product) {
        wishlistRepo.findByUserAndProduct(user, product).ifPresent(wishlistRepo::delete);
    }

    // Get all items
    public List<WishlistItem> getItemsInWishlist(User user) {
        return wishlistRepo.findByUser(user);
    }

}
