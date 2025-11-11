package com.internshipProject1.LearningPLatform.Service;


import com.internshipProject1.LearningPLatform.DTO.QuestionDTO;
import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.DTO.SubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    Quiz addQuiz(Long courseId , QuizDTO quizDTO);
    void deleteQuiz(Long quizId);
    Quiz updateQuiz(Long quizId,QuizDTO quizDTO);
    QuizDTO getQuizById(Long quizId);
    List<QuizDTO> getAll();
    List<QuestionDTO> getQuestions(Long quizId);
    List<SubmissionDTO> getSubmissions(Long quizId);
}
