package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO  {
    private Long quizId;
    private String quizTitle;
    private Double totalMarks;
    private LocalDateTime timestamp;
    private Long courseId;
}
