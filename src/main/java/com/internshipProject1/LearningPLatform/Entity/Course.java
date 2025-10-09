//package com.internshipProject1.LearningPLatform.Entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "course")
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////@RequiredArgsConstructor
//public class Course {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "course_id")
//    private Long courseId;
//
//    @Column(name = "course_title",nullable = false)
//    private String courseTitle;
//
//    @Column(name = "course_description",nullable = false)
//    private String description;
//
//    @Column(name = "course_category",nullable = false)
//    private String category;
//
//    @Column(name = "course_duration",nullable = false)
//    private String duration;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "instructor_id")
//    private Users instructor;
//
//    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Lesson> lessons;
//
//    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<StudentEnrollment> studentEnrollments;
//
//    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Quiz> quiz;
//
//}
