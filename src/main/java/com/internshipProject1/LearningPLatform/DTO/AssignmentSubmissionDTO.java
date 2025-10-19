package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionDTO {
    private Long assignmentSubmissionId;
    private LocalDateTime submissionDate;
    private String assignmentSubmissionUrl;
    private Long userId;
    private Long assignmentId;
    private MultipartFile file;

    public AssignmentSubmissionDTO(Long assignmentSubmissionId, LocalDateTime submissionDate, String assignmentSubmissionUrl, Long userId, Long assignmentId) {
        this.assignmentSubmissionId = assignmentSubmissionId;
        this.submissionDate = submissionDate;
        this.assignmentSubmissionUrl =assignmentSubmissionUrl;
        this.userId = userId;
        this.assignmentId = assignmentId;
    }

}
