package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6L;

    private Long quizId;
    private String quizTitle;
    private Double totalMarks;
    private LocalDateTime timestamp;
}
