package com.noobs.CampusCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noobs.CampusCart.model.CartItem;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
