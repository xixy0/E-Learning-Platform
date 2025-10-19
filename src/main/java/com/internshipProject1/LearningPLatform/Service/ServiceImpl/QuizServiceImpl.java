package com.internshipProject1.LearningPLatform.Service.ServiceImpl;


import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Questions;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import com.internshipProject1.LearningPLatform.Entity.Submission;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Service.QuizService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Long courseId ,QuizDTO quizDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));

        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        Quiz quiz = new Quiz();
        quiz.setQuizTitle(quizDTO.getQuizTitle());
        quiz.setCourse(course);
        quiz.setTotalMarks(quizDTO.getTotalMarks());
        quiz.setTimestamp(LocalDate.now());
        quiz.setQuestions(new ArrayList<>());
        quiz.setSubmissions(new ArrayList<>());

        return quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(Long quizId) {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        quizRepository.deleteById(quizId);
    }

    @Override
    public Quiz updateQuiz(Long quizId, QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        quiz.setQuizTitle(quizDTO.getQuizTitle());
        quiz.setTotalMarks(quizDTO.getTotalMarks());
        quiz.setTimestamp(LocalDate.now());

        return quizRepository.save(quiz);

    }

    @Override
    public Quiz getQuizById(Long quizId){
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
       return quiz;
    }

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public List<Questions> getQuestions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));

        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        return quiz.getQuestions();
    }

    @Override
    public List<Submission> getSubmissions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));

        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        return quiz.getSubmissions();
    }


}
