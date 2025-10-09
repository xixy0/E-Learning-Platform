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
//@Table(name = "questions")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
//public class Questions {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "question_id")
//    private Long questionId;
//
//    @Column(name = "question_text")
//    private String questionText;
//
//    @ElementCollection
//    @CollectionTable(name = "questions_table",joinColumns = @JoinColumn(name ="question_id"))
//    @Column(name = "question_options")
//    private List<String> options;
//
//    @Column(name = "correctOption")
//    private String correctAnswer;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "quiz_id",referencedColumnName = "quiz_id")
//    private Quiz quiz;
//
//}
