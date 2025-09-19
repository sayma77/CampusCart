package com.noobs.CampusCart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.OrderItem;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository,
                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Order placeOrder(Order order) {
        // Save the order first
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = savedOrder.getOrderItems();
        if (orderItems == null || orderItems.isEmpty()) {
            System.out.println("No items in order, skipping notifications.");
            return savedOrder;
        }

        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            if (product == null) {
                System.out.println("OrderItem has no product, skipping notification.");
                continue;
            }

            User seller = product.getUser();
            if (seller == null) {
                System.out.println("Product '" + product.getName() + "' has no seller, skipping notification.");
                continue;
            }

            notificationService.createNotification(
                seller,
                "ORDER_PLACED",
                "Your product '" + product.getName() + 
                "' (Qty: " + item.getQuantity() + 
                ") has been ordered by " + order.getUser().getUsername() + "!"
            );
        }

        return savedOrder;
    }
}
