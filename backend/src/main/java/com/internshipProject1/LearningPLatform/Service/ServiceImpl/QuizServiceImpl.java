package com.internshipProject1.LearningPLatform.Service.ServiceImpl;


import com.internshipProject1.LearningPLatform.DTO.QuestionDTO;
import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.DTO.SubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.*;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @CacheEvict(value = {"quizzes","quizQuestion","quizSubmission","courseQuiz"},allEntries = true)
    public Quiz addQuiz(Long courseId ,QuizDTO quizDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();

        if(!users.getLogin().getRole().equalsIgnoreCase("ADMIN")
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

        notificationService.createAndSend(users,
                "QUIZ_ADDED",
                "Title: "+quiz.getQuizTitle(),
                "Quiz added",
                "Timestamp: "+ quiz.getTimestamp());
        return quizRepository.save(quiz);
    }

    @Override
    @CacheEvict(value = {"quizzes","quizQuestion","quizSubmission","courseQuiz"},allEntries = true)
    public void deleteQuiz(Long quizId) {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        quizRepository.deleteById(quizId);
    }

    @Override
    @CacheEvict(value = {"quizzes","quizQuestion","quizSubmission","courseQuiz"},allEntries = true)
    public Quiz updateQuiz(Long quizId, QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();

        if(!users.getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
        if(!quizDTO.getQuizTitle().isEmpty())
            quiz.setQuizTitle(quizDTO.getQuizTitle());

        if(quizDTO.getTotalMarks()!=null)
            quiz.setTotalMarks(quizDTO.getTotalMarks());

        if(quizDTO.getTimestamp()!=null)
            quiz.setTimestamp(LocalDateTime.now());

        notificationService.createAndSend(users,
                "QUIZ_UPDATED",
                "Title: "+quiz.getQuizTitle(),
                "Quiz updated",
                "Timestamp: "+ quiz.getTimestamp());
        return quizRepository.save(quiz);

    }

    @Override
    @Cacheable(value = "quizzes",key = "#quizId")
    public QuizDTO getQuizById(Long quizId){
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
       return new QuizDTO(
               quiz.getQuizId(),
               quiz.getQuizTitle(),
               quiz.getTotalMarks(),
               quiz.getTimestamp(),
               quiz.getCourse().getCourseId());
    }

    @Override
    @Cacheable(value = "quizzes",key="'all'")
    public List<QuizDTO> getAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for(Quiz quiz : quizzes){
            quizDTOList.add(new QuizDTO(
                    quiz.getQuizId(),
                    quiz.getQuizTitle(),
                    quiz.getTotalMarks(),
                    quiz.getTimestamp(),
                    quiz.getCourse().getCourseId()));
        }
        return quizDTOList;
    }

    @Override
    @Cacheable(value = "quizQuestion",key = "#quizId")
    public List<QuestionDTO> getQuestions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));

        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        List<Questions> questions = quiz.getQuestions();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Questions question : questions){
            questionDTOList.add(new QuestionDTO(
                    question.getQuiz().getQuizId(),
                    question.getQuestionId(),
                    question.getQuestionText(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4(),
                    question.getCorrectAnswer()));
        }
        return questionDTOList;
    }

    @Override
    @Cacheable(value = "quizSubmission",key = "#quizId")
    public List<SubmissionDTO> getSubmissions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Quiz not found"));

        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(quiz.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        List<Submission> submissions = quiz.getSubmissions();
        List<SubmissionDTO> submissionDTOList = new ArrayList<>();

        for(Submission submission:submissions){
            submissionDTOList.add(new SubmissionDTO(
                    submission.getSubmissionId(),
                    submission.getQuiz().getQuizId(),
                    submission.getStudent().getUserId(),
                    submission.getScore(),
                    submission.getTimestamp()));
        }
        return submissionDTOList;
    }


}
