package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user; // your Users entity

    private String type; // e.g., "LESSON_COMPLETED", "ASSIGNMENT_GRADED"
    private String title;
    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String payload; // optional JSON string for extra data (quizId, courseId ...)

    private boolean readFlag = false;

    private Instant createdAt = Instant.now();

    // getters/setters / lombok annotations
}
