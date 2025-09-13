package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.noobs.CampusCart.model.CartItem;
import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CartItemRepository;
import com.noobs.CampusCart.repository.OrderRepository;
import com.noobs.CampusCart.repository.UserRepository;
import com.noobs.CampusCart.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String viewOrders(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        ;
        List<Order> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);
        return "orders"; // orders.html
    }

    @PostMapping("/order/checkout")
    public String checkout(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            return "redirect:/marketplace";
        }

        // Calculate total
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalAmount(total);

        // Map products
        List<Product> products = cartItems.stream()
                .map(CartItem::getProduct)
                .toList();
        order.setProducts(products);

        orderService.placeOrder(order);
        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "redirect:/profile";
    }

}
