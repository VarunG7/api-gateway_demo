package com.microserrvices.notification.service;

import com.microserrvices.notification.model.Notification;
import com.microserrvices.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public Notification sendNotification(String recipientEmail, String subject, String message) {
        String id = UUID.randomUUID().toString();
        // Simulating sending notification logic & persisting record
        Notification notification = new Notification(
                id,
                recipientEmail,
                subject,
                message,
                "SENT",
                LocalDateTime.now()
        );

        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsForRecipient(String email) {
        return notificationRepository.findByRecipientEmailIgnoreCase(email);
    }
}
