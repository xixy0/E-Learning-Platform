package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
}
