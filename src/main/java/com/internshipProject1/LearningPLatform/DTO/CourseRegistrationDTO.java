package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationDTO {
    private String courseTitle;
    private String courseDescription;
    private String courseCategory;
    private String courseDuration;

}
