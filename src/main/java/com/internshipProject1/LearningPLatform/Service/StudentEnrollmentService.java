package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.StudentEnrollmentDTO;
import com.internshipProject1.LearningPLatform.Entity.StudentEnrollment;

import java.util.List;

public interface StudentEnrollmentService {
     StudentEnrollment enroll(Long courseId);
     void unEnroll(Long courseId);
     List<StudentEnrollmentDTO> getAll();
     StudentEnrollmentDTO getEnrollmentById(Long studentEnrollmentId);
   // public Double completionPercentage();


}
