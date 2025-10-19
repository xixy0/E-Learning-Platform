package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.SubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.*;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Repository.SubmissionRepository;
import com.internshipProject1.LearningPLatform.Service.SubmissionService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private UserService userService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public Submission addSubmission(SubmissionDTO submissionDTO) {
        Quiz quiz = quizRepository.findById(submissionDTO.getQuizId()).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
        Users users = userService.getLoggedInUser();
        List<Course> courses = users.getCourses();
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")) {
            boolean c = false;
            for (Course course : courses) {
                if (Objects.equals(course.getCourseId(), quiz.getCourse().getCourseId())) {
                    c = true;
                    break;
                }
            }
            if (!c) {
                throw new RuntimeException("Student not enrolled");
            }
        }

        double score = 0.0;
        int totalQuestions = quiz.getQuestions().size();

        for(Questions q : quiz.getQuestions()){
            String userAnswer = submissionDTO.getAnswers().get(q.getQuestionId());
            if(q.getCorrectAnswer().equalsIgnoreCase(userAnswer)){
                score++;
            }
        }
            double finalScore = (score/totalQuestions)*100;
        Submission submission =new Submission();
        submission.setQuiz(quiz);
        submission.setStudent(users);
        submission.setAnswers(submissionDTO.getAnswers());
        submission.setScore(finalScore);
        submission.setTimestamp(LocalDateTime.now());
        return submissionRepository.save(submission);

    }

    @Override
    public void deleteSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                ()->new RuntimeException("No submission found"));

      submissionRepository.deleteById(submissionId);
    }

    @Override
    public List<Submission> getAll() {
        return submissionRepository.findAll();

    }


}
