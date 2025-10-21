package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String courseCategory;
    private String courseDuration;
    private String instructorName;
    private Integer numberOfStudentsEnrolled;

    public CourseRegistrationDTO(Long courseId, String courseTitle, String courseDescription, String courseCategory, String courseDuration, String instructorName) {
        this.courseId =courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseCategory = courseCategory;
        this.courseDuration =courseDuration;
        this.instructorName = instructorName;
    }
}
