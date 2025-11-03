package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.NotificationDTO;
import com.internshipProject1.LearningPLatform.Entity.Notification;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
@Service
public interface NotificationService {
    Notification createAndSend(Users user, String type, String title, String message, String payload);
    void sendSse(String username, NotificationDTO dto);
    void markRead(Long notificationId, Users user);
    SseEmitter registerSse(String username);
    NotificationDTO toDto(Notification n);
    List<NotificationDTO> fetchForUser(Users user);
}
