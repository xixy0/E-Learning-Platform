package com.internshipProject1.LearningPLatform.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {

    private Long courseId;
    private Long lessonId;
    private String lessonTitle;
    private String lessonDescription;
    private String videoUrl;
    private String pdfUrl;
    private String instructorName;
    private MultipartFile file;

    public LessonDTO(Long courseId, Long lessonId, String lessonTitle, String lessonDescription, String videoUrl, String pdfUrl, String instructorName) {
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.lessonDescription = lessonDescription;
        this.videoUrl = videoUrl;
        this.pdfUrl = pdfUrl;
        this.instructorName = instructorName;
    }
}
