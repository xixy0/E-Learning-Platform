//package com.internshipProject1.LearningPLatform.Entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "enrollemnts")
//@Data
//@AllArgsConstructor
//@RequiredArgsConstructor
//@NoArgsConstructor
//public class StudentEnrollment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "enrollment_id")
//    private Long enrollmentId;
//
//    @Column(name = "enrollment_date")
//    private LocalDate enrollmentDate;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id",nullable = false,referencedColumnName = "user_id")
//    private Users users;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id",nullable = false,referencedColumnName = "course_id")
//    private Course course;
//
//
//}
