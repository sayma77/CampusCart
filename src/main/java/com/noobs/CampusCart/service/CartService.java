package com.noobs.CampusCart.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noobs.CampusCart.model.CartItem;
import com.noobs.CampusCart.model.Notification;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CartItemRepository;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(User user, Product product, int quantity) {
        CartItem item = cartItemRepository.findByUser(user).stream()
                .filter(ci -> ci.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setAddedDate(LocalDateTime.now());
        }
        cartItemRepository.save(item);
    }

    public void removeFromCart(User user, Product product) {
        cartItemRepository.findByUserAndProduct(user, product).ifPresent(cartItemRepository::delete);
    }

    public List<CartItem> getItemsInCart(User user) {
        List<CartItem> items = cartItemRepository.findByUser(user);
        return items;
    }
}
