package com.noobs.CampusCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noobs.CampusCart.model.OrderItem;
import com.noobs.CampusCart.model.OrderItemId;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
