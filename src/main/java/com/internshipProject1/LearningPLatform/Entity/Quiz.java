package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;
    private String quizTitle;
    private Integer totalMarks;
    private LocalDate timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id",referencedColumnName = "course_id",nullable = false)
    private Course course;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Questions> questions;

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Submission> submissions;
}
