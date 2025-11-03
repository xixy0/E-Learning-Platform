package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.NotificationDTO;
import com.internshipProject1.LearningPLatform.Entity.Notification;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.NotificationRepository;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Map<String, List<SseEmitter>> sseEmitters = new ConcurrentHashMap<>();

    @Override
    public Notification createAndSend(Users user, String type, String title, String message, String payload) {
        Notification n = new Notification();
        n.setUser(user);
        n.setType(type);
        n.setTitle(title);
        n.setMessage(message);
        n.setPayload(payload);
        n = notificationRepository.save(n);

        NotificationDTO dto = toDto(n);

        // send via websocket to the specific user (principal name must match user.getLogin().getUsername())
        String username = user.getLogin().getUsername();
        messagingTemplate.convertAndSendToUser(username, "/queue/notifications", dto);

        // send via SSE fallback
        sendSse(username, dto);

        return n;
    }

    @Override
    public void sendSse(String username, NotificationDTO dto) {
        List<SseEmitter> list = sseEmitters.get(username);
        if (list == null) return;
        List<SseEmitter> failed = new ArrayList<>();
        for (SseEmitter emitter : list) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(dto));
            } catch (Exception e) {
                emitter.completeWithError(e);
                failed.add(emitter);
            }
        }
        list.removeAll(failed);
    }

    @Override
    public void markRead(Long notificationId, Users user) {
        Notification n = notificationRepository.findById(notificationId).orElseThrow();
        if (!n.getUser().getUserId().equals(user.getUserId())) throw new RuntimeException("Not owner");
        n.setReadFlag(true);
        notificationRepository.save(n);
    }

    @Override
    public SseEmitter registerSse(String username) {
        SseEmitter emitter = new SseEmitter(0L); // 0 = infinite or set timeout
        sseEmitters.computeIfAbsent(username, k -> new CopyOnWriteArrayList<>()).add(emitter);
        emitter.onCompletion(() -> sseEmitters.getOrDefault(username, List.of()).remove(emitter));
        emitter.onTimeout(() -> sseEmitters.getOrDefault(username, List.of()).remove(emitter));
        return emitter;
    }

    @Override
    public NotificationDTO toDto(Notification n) {
        return new NotificationDTO(n.getId(), n.getType(), n.getTitle(), n.getMessage(), n.getPayload(), n.isReadFlag(), n.getCreatedAt());
    }

    @Override
    public List<NotificationDTO> fetchForUser(Users user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user).stream().map(this::toDto).toList();
    }
}
