package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationDTO {

    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String courseCategory;
    private String instructorName;
    private Integer numberOfStudentsEnrolled;

    public CourseRegistrationDTO(Long courseId, String courseTitle, String courseDescription, String courseCategory, String instructorName) {
        this.courseId =courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseCategory = courseCategory;
        this.instructorName = instructorName;
    }
}
