package com.noobs.CampusCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noobs.CampusCart.model.Notification;
import com.noobs.CampusCart.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
