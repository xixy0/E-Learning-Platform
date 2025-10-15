package com.internshipProject1.LearningPLatform.Service.ServiceImpl;


import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Questions;
import com.internshipProject1.LearningPLatform.Entity.Quiz;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Service.QuizService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Quiz addQuiz(QuizDTO quizDTO) {
        Course course = courseRepository.findByCourseTitle(quizDTO.getCourseTitle()).orElseThrow(()->new RuntimeException("Course not found"));

        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        Quiz quiz = new Quiz();
        quiz.setQuizTitle(quiz.getQuizTitle());
        quiz.setCourse(course);
        quiz.setTotalMarks(quizDTO.getTotalMarks());
        quiz.setTimestamp(LocalDate.now());

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
    public QuizDTO updateQuiz(Long quizId, QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
        Course course = quiz.getCourse();
        if(!quizDTO.getCourseTitle().equalsIgnoreCase("")) {
            course = courseRepository.findByCourseTitle(quizDTO.getCourseTitle()).orElseThrow(() -> new RuntimeException("Course not found"));
        }
        quiz.setQuizTitle(quiz.getQuizTitle());
        quiz.setCourse(course);
        quiz.setTotalMarks(quizDTO.getTotalMarks());
        quiz.setTimestamp(LocalDate.now());

        quizRepository.save(quiz);
        return quizDTO;
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


}
