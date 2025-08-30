package com.noobs.CampusCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    Optional<Order> findByUserAndProducts(User user, Product product);
}
