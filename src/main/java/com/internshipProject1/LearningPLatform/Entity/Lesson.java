package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Entity
@Table(name="lesson")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;


    @Column(name = "title")
    private String lessonTitle;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<LessonProgress> progresses;
}
