package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonProgressId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "lesson_id",referencedColumnName = "lesson_id")
    private Lesson lesson;

    private float playedTime;
    private float duration;

}
