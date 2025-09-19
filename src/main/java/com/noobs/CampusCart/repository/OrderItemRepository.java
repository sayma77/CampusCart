package com.noobs.CampusCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noobs.CampusCart.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {

}
