//package com.internshipProject1.LearningPLatform.Entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "submissions")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
//public class Submission {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "submission_id")
//    private Long submissionId;
//
//    @Column(name = "score")
//    private Double score;
//
//    @Column(name = "timestamp")
//    private LocalDateTime timestamp;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private Users student;
//
//    @ManyToOne
//    private Quiz quiz;
//}
