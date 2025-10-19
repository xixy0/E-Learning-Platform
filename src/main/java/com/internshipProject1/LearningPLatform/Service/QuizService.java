package com.internshipProject1.LearningPLatform.Service;


import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.Entity.Questions;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import com.internshipProject1.LearningPLatform.Entity.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    Quiz addQuiz(Long courseId , QuizDTO quizDTO);
    void deleteQuiz(Long quizId);
    Quiz updateQuiz(Long quizId,QuizDTO quizDTO);
    Quiz getQuizById(Long quizId);
    List<Quiz> getAll();
    List<Questions> getQuestions(Long quizId);
    List<Submission> getSubmissions(Long quizId);




}
