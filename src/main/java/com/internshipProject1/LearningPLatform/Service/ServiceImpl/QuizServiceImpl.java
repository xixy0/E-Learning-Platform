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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
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
    @CacheEvict(value = {"quizzes","quizQuestion","quizSubmission"},allEntries = true)
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
        quiz.setTimestamp(LocalDateTime.now());
        quiz.setQuestions(new ArrayList<>());
        quiz.setSubmissions(new ArrayList<>());

        return quizRepository.save(quiz);
    }

    @Override
    @CacheEvict(value = {"quizzes","quizQuestion","quizSubmission"},allEntries = true)
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
    @CacheEvict(value = {"quizzes","quizQuestion","quizSubmission"},allEntries = true)
    public Quiz updateQuiz(Long quizId, QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        quiz.setQuizTitle(quizDTO.getQuizTitle());
        quiz.setTotalMarks(quizDTO.getTotalMarks());
        quiz.setTimestamp(LocalDateTime.now());

        return quizRepository.save(quiz);

    }

    @Override
    @Cacheable(value = "quizzes",key = "#quizId")
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
    @Cacheable(value = "quizzes",key="'all'")
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    @Cacheable(value = "quizQuestion",key = "#quizId")
    public List<Questions> getQuestions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));

        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        return quiz.getQuestions();
    }

    @Override
    @Cacheable(value = "quizSubmission",key = "#quizId")
    public List<Submission> getSubmissions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));

        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        return quiz.getSubmissions();
    }


}
