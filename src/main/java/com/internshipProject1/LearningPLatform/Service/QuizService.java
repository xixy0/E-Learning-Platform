package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    Quiz addQuiz(QuizDTO quizDTO);
    void deleteQuiz(Long quizId);
    QuizDTO updateQuiz(Long quizId,QuizDTO quizDTO);
    //Quiz addQuestions(Long quizId, Questions questions);
    //Question getQuestions(Long quizId)
    Quiz getQuizById(Long quizId);
    List<Quiz> getAll();


}
