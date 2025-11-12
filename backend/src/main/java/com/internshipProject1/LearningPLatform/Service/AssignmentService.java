package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.AssignmentDTO;
import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Assignment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AssignmentService {
    Assignment addAssignment(Long courseId, AssignmentDTO assignmentDTO);
    Assignment updateAssignment(Long assignmentId,AssignmentDTO assignmentDTO);
    void deleteAssignment(Long assignmentId);
    List<AssignmentDTO> getAll();
    String uploadAssignmentPdf(Long assignmentId, MultipartFile file);
    void removeAssignmentPdf(Long assignmentId,String path);
    List<AssignmentSubmissionDTO> getAllCourseAssignmentSubmissions(Long assignmentId);
    AssignmentDTO getAssignmentById(Long assignmentId);
    String addAssignmentPdf(Long assignmentId,AssignmentDTO assignmentDTO);
}
