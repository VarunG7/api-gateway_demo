package com.microserrvices.notification.controller;

import com.microserrvices.notification.model.Notification;
import com.microserrvices.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody Map<String, String> payload) {
        String recipient = payload.get("recipientEmail");
        String subject = payload.get("subject");
        String message = payload.get("message");
        return ResponseEntity.ok(notificationService.sendNotification(recipient, subject, message));
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/recipient")
    public List<Notification> getNotificationsByRecipient(@RequestParam String email) {
        return notificationService.getNotificationsForRecipient(email);
    }
}
