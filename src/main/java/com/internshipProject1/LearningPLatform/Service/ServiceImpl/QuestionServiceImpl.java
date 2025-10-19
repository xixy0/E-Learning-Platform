package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.QuestionDTO;
import com.internshipProject1.LearningPLatform.Entity.Questions;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import com.internshipProject1.LearningPLatform.Repository.QuestionRepository;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Service.QuestionService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Questions addQuestion(QuestionDTO questionDTO) {
       Quiz quiz = quizRepository.findByQuizTitle(questionDTO.getQuizTitle()).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        Questions questions = new Questions();
        questions.setQuestionText(questionDTO.getQuestionText());
        questions.setOption1(questionDTO.getOption1());
        questions.setOption2(questionDTO.getOption2());
        questions.setOption3(questionDTO.getOption3());
        questions.setOption4(questionDTO.getOption4());
        questions.setCorrectAnswer(questionDTO.getCorrectAnswer());
        questions.setQuiz(quiz);
        questionRepository.save(questions);
        return questions;

    }

    @Override
    public void deleteQuestion(Long questionId) {
        Questions questions = questionRepository.findById(questionId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(questions.getQuiz().getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        questionRepository.deleteById(questionId);
    }


    @Override
    public Questions updateQuestion(Long questionId, QuestionDTO questionDTO) {
        Questions question = questionRepository.findById(questionId).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
        Quiz quiz = quizRepository.findByQuizTitle(questionDTO.getQuizTitle()).orElseThrow(
                ()->new RuntimeException("Quiz not found"));

        if (!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(question.getQuiz().getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())) {
            throw new RuntimeException("Unauthorized Instructor");
        }
        question.setQuestionText(questionDTO.getQuestionText());
        question.setQuiz(quiz);
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        return questionRepository.save(question);


    }
    @Override
    public List<QuestionDTO> getAll() {

        List<Questions> questions = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Questions question : questions){
         questionDTOList.add(new QuestionDTO(question.getQuestionText(),
                 question.getOption1(), question.getOption2(), question.getOption3(),
                 question.getOption4(), question.getCorrectAnswer(),
                 question.getQuiz().getQuizTitle()));
        }

        return questionDTOList;
    }
}
