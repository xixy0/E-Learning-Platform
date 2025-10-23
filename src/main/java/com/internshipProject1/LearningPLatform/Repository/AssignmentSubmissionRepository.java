package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
}
