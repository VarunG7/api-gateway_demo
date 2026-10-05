package com.microserrvices.notification.repository;

import com.microserrvices.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByRecipientEmailIgnoreCase(String recipientEmail);
}
