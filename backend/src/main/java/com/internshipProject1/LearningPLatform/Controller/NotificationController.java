package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.NotificationDTO;
import com.internshipProject1.LearningPLatform.DTO.UserDTO;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Security.JwtService;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService; // use your getLoggedInUser()
    @Autowired
    private UserRepository userRepository; // use your getLoggedInUser()
    @Autowired
    private JwtService jwtService;

    @GetMapping("/getMyNotifications")
    public List<NotificationDTO> getMyNotifications() {
        UserDTO meDTO = userService.getLoggedInUser();
        Users me = userRepository.findById(meDTO.getUserId()).get();
        return notificationService.fetchForUser(me);
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<?> markRead(@PathVariable Long notificationId) {
        UserDTO meDTO = userService.getLoggedInUser();
        Users me = userRepository.findById(meDTO.getUserId()).get();
        notificationService.markRead(notificationId, me);
        return ResponseEntity.ok().build();
    }

    // SSE registration
    @GetMapping("/sse")
    public SseEmitter sse() {
        UserDTO meDTO = userService.getLoggedInUser();
        Users me = userRepository.findById(meDTO.getUserId()).get();
        return notificationService.registerSse(me.getLogin().getUsername());
    }

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(HttpServletRequest request) throws AccessDeniedException {
            // Get the Authorization header
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AccessDeniedException("Missing or invalid Authorization header");
            }

            // Extract token
            String token = authHeader.substring(7);

            // Validate token
            try {
                String username = jwtService.extractUsername(token);

                if (jwtService.isTokenExpired(token)) {
                    throw new AccessDeniedException("Token expired");
                }

                // Register SSE for this user
                return notificationService.registerSse(username);

            } catch (Exception e) {
                throw new AccessDeniedException("Invalid or expired token");
            }
        }




}
