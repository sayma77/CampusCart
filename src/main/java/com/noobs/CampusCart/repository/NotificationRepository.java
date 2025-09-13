package com.noobs.CampusCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.noobs.CampusCart.model.Notification;
import com.noobs.CampusCart.model.User;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
