package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.AuthRequest;
import com.internshipProject1.LearningPLatform.DTO.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    void clearAllCaches();
}
