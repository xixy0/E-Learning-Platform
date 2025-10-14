package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "quiz")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "quiz_title",nullable = false)
    private String quizTitle;

    @Column(name = "quiz_marks")
    private Double totalMarks;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id",referencedColumnName = "course_id",nullable = false)
    private Course course;

//    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Questions> questions;
//
//    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Submission> submissions;
}
