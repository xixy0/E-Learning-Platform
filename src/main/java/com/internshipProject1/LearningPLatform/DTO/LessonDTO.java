package com.internshipProject1.LearningPLatform.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private Long courseId;
    private Long lessonId;
    private String lessonTitle;
    private String lessonDescription;
    private String videoUrl;
    private String pdfUrl;
    private String instructorName;
}
