package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.NotificationDTO;
import com.internshipProject1.LearningPLatform.DTO.UserDTO;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired private NotificationService notificationService;
    @Autowired private UserService userService; // use your getLoggedInUser()
    @Autowired private UserRepository userRepository; // use your getLoggedInUser()

    @GetMapping("/my")
    public List<NotificationDTO> getMyNotifications() {
        UserDTO meDTO = userService.getLoggedInUser();
        Users me = userRepository.findById(meDTO.getUserId()).get();
        return notificationService.fetchForUser(me);
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<?> markRead(@PathVariable Long id) {
        UserDTO meDTO = userService.getLoggedInUser();
        Users me = userRepository.findById(meDTO.getUserId()).get();
        notificationService.markRead(id, me);
        return ResponseEntity.ok().build();
    }

    // SSE registration
    @GetMapping("/sse")
    public SseEmitter sse() {
        UserDTO meDTO = userService.getLoggedInUser();
        Users me = userRepository.findById(meDTO.getUserId()).get();
        return notificationService.registerSse(me.getLogin().getUsername());
    }

}
