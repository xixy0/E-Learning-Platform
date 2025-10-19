package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.AssignmentSubmission;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AssignmentSubmissionService {
    AssignmentSubmission addAssignmentSubmission(Long assignmentId,AssignmentSubmissionDTO assignmentSubmissionDTO);
    void deleteAssignmentSubmission(Long assignmentSubmissionId);
    String uploadAssignmentSubmission(Long assignmentId ,MultipartFile file);
    AssignmentSubmission getAssignmentSubmissionByUser(Long assignmentId);
    List<AssignmentSubmission> getAll();
}
