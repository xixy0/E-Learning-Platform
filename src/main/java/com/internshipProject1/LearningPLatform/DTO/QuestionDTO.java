package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5L;

    private Long quizId;
    private Long questionId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
}
