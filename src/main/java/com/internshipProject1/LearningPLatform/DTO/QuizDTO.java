package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {

    private String quizTitle;
    private Double totalMarks;
    private String  courseTitle;
    private LocalDate timestamp;

}
