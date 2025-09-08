package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noobs.CampusCart.model.Order;
import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.OrderRepository;
import com.noobs.CampusCart.repository.ProductRepository;
import com.noobs.CampusCart.service.UserService;

@Controller
public class ProfilePageController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private final UserService userService;

    public ProfilePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profilePage(
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            Model model,
            Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        Map<String, String> user_data = new HashMap<>();
        user_data.put("name", user.getName());
        user_data.put("username", user.getUsername());
        user_data.put("email", user.getEmail());
        user_data.put("hall", user.getHall());

        List<Order> orders = List.of();
        List<Product> products = List.of();

        switch (filter) {
            case "buy":
                orders = orderRepository.findByUser(user).stream()
                        .map(order -> {
                            order.setProducts(
                                    order.getProducts().stream()
                                            .filter(p -> p.getSellOrRent().equalsIgnoreCase("sell"))
                                            .toList());
                            return order;
                        })
                        .filter(o -> !o.getProducts().isEmpty())
                        .toList();
                break;

            case "taken_rent":
                orders = orderRepository.findByUser(user).stream()
                        .map(order -> {
                            order.setProducts(
                                    order.getProducts().stream()
                                            .filter(p -> p.getSellOrRent().equalsIgnoreCase("rent"))
                                            .toList());
                            return order;
                        })
                        .filter(o -> !o.getProducts().isEmpty())
                        .toList();
                break;

            case "sell":
                products = productRepository.findByUserAndSellOrRent(user, "sell");
                break;

            case "given_rent":
                products = productRepository.findByUserAndSellOrRent(user, "rent");
                break;

        }

        // Format dates for orders
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        for (Order order : orders) {
            order.setFormattedDate(order.getOrderDate().format(formatter));
        }

        model.addAttribute("user", user_data);
        model.addAttribute("orders", orders);
        model.addAttribute("products", products);
        model.addAttribute("selectedFilter", filter);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("hall") String hall,
            @RequestParam(value = "password", required = false) String password,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        // Fetch existing user
        User user = userService.getUserByEmail(principal.getName());

        // Update only fields that are non-empty
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }
        // if (username != null && !username.isEmpty()) {
        // user.setUsername(username);
        // }
        if (hall != null && !hall.isEmpty()) {
            user.setHall(hall);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password); // will be encoded in service
        }

        // Save updates using service
        userService.updateProfile(principal.getName(), user);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }
}
