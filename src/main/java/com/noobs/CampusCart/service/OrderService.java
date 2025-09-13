package com.noobs.CampusCart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.OrderRepository;

import java.util.List;

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
        // Save the order
        Order savedOrder = orderRepository.save(order);

        List<Product> products = savedOrder.getProducts();
        if (products == null || products.isEmpty()) {
            System.out.println("No products in order, skipping notifications.");
            return savedOrder;
        }

        for (Product product : products) {
            User seller = product.getUser(); // could be null
            if (seller == null) {
                System.out.println("Product '" + product.getName() + "' has no seller, skipping notification.");
                continue;
            }

            notificationService.createNotification(
                seller,
                "ORDER_PLACED",
                "Your product '" + product.getName() + "' has been ordered by " + order.getUser().getUsername() + "!"
            );
        }

        return savedOrder;
    }
}
