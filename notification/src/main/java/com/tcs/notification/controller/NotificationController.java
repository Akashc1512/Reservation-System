package com.tcs.notification.controller;

import com.tcs.notification.entities.Notification;
import com.tcs.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
    NotificationService notificationService;
	
    @PostMapping("/send")
    public String sendNotification() {
		return null;
    }
    
    @GetMapping("/customer/{CustomerId}")
    public List<Notification> getNotificationBycautomerId(@PathVariable Long CustomerId) {
		return notificationService.getAllNotifications(CustomerId);
    }
    
}
