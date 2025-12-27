package com.codingshuttle.linkedin.notification_service.respository;

import com.codingshuttle.linkedin.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
