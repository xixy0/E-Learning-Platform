package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.SubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.*;
import com.internshipProject1.LearningPLatform.Repository.QuizRepository;
import com.internshipProject1.LearningPLatform.Repository.SubmissionRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
import com.internshipProject1.LearningPLatform.Service.SubmissionService;
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
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    @CacheEvict(value = {"submissions","userSubmissions","quizSubmission"},allEntries = true)
    public Submission addSubmission(SubmissionDTO submissionDTO) {
        Quiz quiz = quizRepository.findById(submissionDTO.getQuizId()).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();
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
        Submission savedSubmission = submissionRepository.save(submission);
        notificationService.createAndSend(users,
                "QUIZ_SUBMITTED",
                "User: "+ users.getFirstName() +" "+users.getMiddleName()+ " "+users.getLastName(),
                "Quiz submitted and graded",
                "Username: "+ users.getLogin().getUsername() + " Score: "+ finalScore);
        return savedSubmission;

    }

    @Override
    @CacheEvict(value = {"submissions","userSubmissions","quizSubmission"},allEntries = true)
    public void deleteSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                ()->new RuntimeException("No submission found"));

      submissionRepository.deleteById(submissionId);
    }

    @Override
    @Cacheable(value = "submissions",key = "'all'")
    public List<SubmissionDTO> getAll() {
        List<Submission> submissions = submissionRepository.findAll();
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

    @Override
    @Cacheable(value = "submissions",key="#subimissionId")
    public SubmissionDTO getSubmissionById(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                ()->new RuntimeException("No submission found"));
        return new SubmissionDTO(
                submission.getSubmissionId(),
                submission.getQuiz().getQuizId(),
                submission.getStudent().getUserId(),
                submission.getScore(),
                submission.getTimestamp());
    }


}
