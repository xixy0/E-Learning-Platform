package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_title",nullable = false)
    private String courseTitle;

    @Column(name = "course_description",nullable = false)
    private String courseDescription;

    @Column(name = "course_category",nullable = false)
    private String courseCategory;

    @Column(name = "course_duration",nullable = false)
    private String courseDuration;


//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Users instructor;

    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Lesson> lessons;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<StudentEnrollment> studentEnrollments;

//    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Quiz> quiz;

}
