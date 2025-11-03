package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



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
    private String imageUrl;

    public CourseRegistrationDTO(Long courseId, String courseTitle, String courseDescription, String courseCategory, String instructorName) {
        this.courseId =courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseCategory = courseCategory;
        this.instructorName = instructorName;
    }
}
