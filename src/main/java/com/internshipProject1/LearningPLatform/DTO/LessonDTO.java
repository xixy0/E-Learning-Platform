package com.internshipProject1.LearningPLatform.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private String lessonDescription;
    private String lessonTitle;
    private String courseTitle;
    private String videoUrl;
    private String pdfUrl;
    private String instructor;
}
