package com.noobs.CampusCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.model.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUser(User user);

    Optional<WishlistItem> findByUserAndProduct(User user, Product product);
}
