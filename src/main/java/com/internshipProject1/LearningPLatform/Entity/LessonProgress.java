package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_progress")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressId;

    private boolean completed = false;

    @Column(name = "Lesson_status")
    private String lessonStatus;
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id",referencedColumnName = "lesson_id",nullable = false)
    private Lesson lesson;

}
