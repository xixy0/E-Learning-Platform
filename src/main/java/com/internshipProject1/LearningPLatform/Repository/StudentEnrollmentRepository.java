package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment,Long> {

}
