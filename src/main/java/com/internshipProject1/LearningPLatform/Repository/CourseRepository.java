package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

}
