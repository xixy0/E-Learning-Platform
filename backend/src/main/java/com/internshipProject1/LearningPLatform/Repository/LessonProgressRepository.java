package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.Lesson;
import com.internshipProject1.LearningPLatform.Entity.LessonProgress;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress,Long> {

    Optional<LessonProgress> findByUsersAndLesson(Users users, Lesson lesson);
}
