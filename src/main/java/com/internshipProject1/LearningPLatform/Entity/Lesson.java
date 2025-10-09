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
//
//@Entity
//@Table(name="lesson")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
//public class Lesson {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "lesson_id")
//    private Long lessonId;
//
//    @Lob
//    private String content;
//
//    private boolean isComplete;
//
//    private List<String> videourls;
//    private String pdfUrl;
//
//    @Column(name = "title")
//    private String lessonTitle;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id",nullable = false,referencedColumnName = "user_id")
//    private Users users;
//
//
//}
