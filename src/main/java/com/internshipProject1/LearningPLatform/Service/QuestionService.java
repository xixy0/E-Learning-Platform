package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.QuestionDTO;
import com.internshipProject1.LearningPLatform.Entity.Questions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    Questions addQuestion(QuestionDTO questionDTO);
    void deleteQuestion(Long questionId);
    Questions updateQuestion(Long questionId,QuestionDTO questionDTO);
    List<QuestionDTO> getAll();

    //public Integer validateAnswers(Long quizId,Long submissionId);
}

