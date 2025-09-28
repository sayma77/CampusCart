package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noobs.CampusCart.model.CartItem;
import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.OrderItem;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.CartItemRepository;
import com.noobs.CampusCart.repository.OrderRepository;
import com.noobs.CampusCart.repository.ProductRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/orders")
    public String viewOrders(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        List<Order> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);
        return "orders"; // orders.html
    }

    @PostMapping("/order/checkout")
    public String checkout(Principal principal, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(principal.getName()).get();

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            return "redirect:/marketplace";
        }

        // Validate stock
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (item.getQuantity() > product.getQuantity()) {
                redirectAttributes.addFlashAttribute("error",
                        "Not enough stock for \"" + product.getName() + "\". Only " + product.getQuantity() + " left.");
                return "redirect:/cart";
            }
        }

        // Calculate total
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // Create new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalAmount(total);

        // Create order items
        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.getId().setProductId(item.getProduct().getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice()); // snapshot
            orderItem.setStatus("PENDING");
            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);

        // Update stock
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            int newQty = Math.max(product.getQuantity() - item.getQuantity(), 0);
            product.setQuantity(newQty);
            productRepository.save(product);
        }

        // Save order (cascades orderItems)
        orderService.placeOrder(order);

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "redirect:/profile";
    }

}
