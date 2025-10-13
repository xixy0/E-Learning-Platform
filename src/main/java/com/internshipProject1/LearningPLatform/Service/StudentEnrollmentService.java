package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.Entity.StudentEnrollment;

public interface StudentEnrollmentService {
    public StudentEnrollment enroll(Long courseId);
    public void unEnroll(Long courseId);
   // public Double completionPercentage();


}
