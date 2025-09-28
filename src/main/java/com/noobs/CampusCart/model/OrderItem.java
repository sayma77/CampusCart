package com.noobs.CampusCart.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_products")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id = new OrderItemId();

    @ManyToOne
    @MapsId("orderId") // maps to OrderItemId.orderId
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId") // maps to OrderItemId.productId
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
    private double price;
    private String status;
}
