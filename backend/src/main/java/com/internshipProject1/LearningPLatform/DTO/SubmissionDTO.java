package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDTO {

    private Long submissionId;
    private Long quizId;
    private Long studentId;
    private double score;
    private LocalDateTime timestamp;
    private Map<Long,String> answers;

    public SubmissionDTO(Long submissionId, Long quizId, Long studentId, Double score, LocalDateTime timestamp) {
        this.submissionId = submissionId;
        this.quizId =quizId;
        this.studentId =studentId;
        this.score =score;
        this.timestamp = timestamp;
    }
}
