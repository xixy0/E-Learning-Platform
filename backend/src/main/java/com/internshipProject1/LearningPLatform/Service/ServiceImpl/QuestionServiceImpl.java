package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.QuestionDTO;
import com.internshipProject1.LearningPLatform.Entity.Questions;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import com.internshipProject1.LearningPLatform.Repository.QuestionRepository;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Service.QuestionService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"questions","quizQuestion"},allEntries = true)
    public Questions addQuestion(QuestionDTO questionDTO) {
       Quiz quiz = quizRepository.findById(questionDTO.getQuizId()).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
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
    @CacheEvict(value = {"questions","quizQuestion"},allEntries = true)
    public void deleteQuestion(Long questionId) {
        Questions questions = questionRepository.findById(questionId).orElseThrow(()->new RuntimeException("Question not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(questions.getQuiz().getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        questionRepository.deleteById(questionId);
    }


    @Override
    @CacheEvict(value = {"questions","quizQuestion"},allEntries = true)
    public Questions updateQuestion(Long questionId, QuestionDTO questionDTO) {
        Questions question = questionRepository.findById(questionId).orElseThrow(
                () -> new RuntimeException("Quiz not found"));


        if (!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(question.getQuiz().getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())) {
            throw new RuntimeException("Unauthorized Instructor");
        }
        if(!questionDTO.getQuestionText().isEmpty())
            question.setQuestionText(questionDTO.getQuestionText());

        if(!questionDTO.getOption1().isEmpty())
            question.setOption1(questionDTO.getOption1());

        if(!questionDTO.getOption2().isEmpty())
            question.setOption2(questionDTO.getOption2());

        if(!questionDTO.getOption3().isEmpty())
            question.setOption3(questionDTO.getOption3());

        if(!questionDTO.getOption4().isEmpty())
            question.setOption4(questionDTO.getOption4());

        if(!questionDTO.getCorrectAnswer().isEmpty())
            question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        return questionRepository.save(question);


    }
    @Override
    @Cacheable(value = "questions",key="'all'")
    public List<QuestionDTO> getAll() {

        List<Questions> questions = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Questions question : questions){
         questionDTOList.add(new QuestionDTO(
                 question.getQuiz().getQuizId(),
                 question.getQuestionId(),question.getQuestionText(),
                 question.getOption1(),
                 question.getOption2(),
                 question.getOption3(),
                 question.getOption4(),
                 question.getCorrectAnswer()));
        }

        return questionDTOList;
    }

    @Override
    @Cacheable(value = "questions",key = "#questionId")
    public QuestionDTO getQuestionById(Long questionId) {
        Questions question = questionRepository.findById(questionId).orElseThrow(
                () -> new RuntimeException("Question not found"));
        return new QuestionDTO(
                question.getQuiz().getQuizId(),
                question.getQuestionId(),
                question.getQuestionText(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4(),
                question.getCorrectAnswer());
    }
}
