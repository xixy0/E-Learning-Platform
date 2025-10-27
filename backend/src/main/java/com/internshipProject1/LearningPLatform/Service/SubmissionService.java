package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.SubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Submission;

import java.util.List;

public interface SubmissionService {
    Submission addSubmission(SubmissionDTO submissionDTO);
    void deleteSubmission(Long submissionId);
    List<SubmissionDTO> getAll();
    SubmissionDTO getSubmissionById(Long submissionId);

}

