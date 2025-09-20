package com.noobs.CampusCart.service;

import com.noobs.CampusCart.model.Notification;
import com.noobs.CampusCart.model.User;
import com.noobs.CampusCart.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Create a new notification
    public void createNotification(User user, String type, String message) {
        if (user == null) {
            System.out.println("Notification not created: User is null");
            return;
        }

        if (message == null || message.isEmpty()) {
            message = type;
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setMessage(message.length() > 500 ? message.substring(0, 500) : message);
        notification.setRead(false);
        notification.setCreatedAt(java.time.LocalDateTime.now());

        notificationRepository.save(notification);
    }

    // Get all notifications for a user, newest first
    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // Mark a single notification as read
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null && !notification.isRead()) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    // Mark all notifications as read for a user
    public void markAllAsRead(User user) {
        List<Notification> notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user);
        for (Notification n : notifications) {
            if (!n.isRead()) {
                n.setRead(true);
            }
        }
        notificationRepository.saveAll(notifications);
    }
}
