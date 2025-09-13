package com.noobs.CampusCart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.noobs.CampusCart.model.Notification;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.UserRepository;
import com.noobs.CampusCart.service.NotificationService;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    // Display notifications page
    @GetMapping("/notifications")
    public String notifications(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Notification> notifications = notificationService.getNotificationsForUser(user);
        long readCount = notifications.stream().filter(Notification::isRead).count();
        long unreadCount = notifications.size() - readCount;

        model.addAttribute("notifications", notifications);
        model.addAttribute("readCount", readCount);
        model.addAttribute("unreadCount", unreadCount);

        return "notifications";
    }

    // Mark a single notification as read
    @GetMapping("/notifications/read/{id}")
    public String markAsRead(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notificationService.markAsRead(id);
        return "redirect:/notifications";
    }

    // Mark all notifications as read
    @GetMapping("/notifications/read-all")
    public String markAllAsRead(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notificationService.markAllAsRead(user);
        return "redirect:/notifications";
    }
}
