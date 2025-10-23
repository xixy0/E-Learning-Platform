package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface QuizRepository extends JpaRepository<Quiz, Long> {
   Optional<Quiz> findByQuizTitle(String quizTitle);
}
