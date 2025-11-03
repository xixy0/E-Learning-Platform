package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.LessonProgressDTO;
import com.internshipProject1.LearningPLatform.Entity.Lesson;
import com.internshipProject1.LearningPLatform.Entity.LessonProgress;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.LessonProgressRepository;
import com.internshipProject1.LearningPLatform.Repository.LessonRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.LessonProgressService;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LessonProgressServiceImpl implements LessonProgressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;


    @Override
    @CacheEvict(value = {"progress"},allEntries = true)
    public LessonProgress updateProgress(LessonProgressDTO lessonProgressDTO) {
        Long userId = lessonProgressDTO.getUserId();
        Long lessonId = lessonProgressDTO.getLessonId();
        float playedTime = lessonProgressDTO.getPlayedTime();
        float duration = lessonProgressDTO.getDuration();

        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN") &&
                !Objects.equals(userService.getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized User");
        }

        Users users = userRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                ()->new RuntimeException("Lesson not found"));

        LessonProgress lessonProgress = new LessonProgress();

        Optional<LessonProgress> optionalLessonProgress = lessonProgressRepository.findByUsersAndLesson(users,lesson);
        if(optionalLessonProgress.isPresent()) {
             lessonProgress = optionalLessonProgress.get();
            if (lessonProgress.getPlayedTime() == 0 || lessonProgress.getPlayedTime() <= playedTime) {
                lessonProgress.setPlayedTime(playedTime);
                lessonProgress.setDuration(duration);
            }
        }
        else {
            lessonProgress.setLesson(lesson);
            lessonProgress.setUsers(users);
            lessonProgress.setPlayedTime(playedTime);
            lessonProgress.setDuration(duration);
        }
        LessonProgress lessonProgress1 =  lessonProgressRepository.save(lessonProgress);
        float completePercent = (lessonProgress1.getPlayedTime()/lessonProgress1.getDuration())*100;
        if(completePercent<100){
            notificationService.createAndSend(users,
                    "UPDATED",
                    "Progress updated",
                    "Your progress has been successfully updated",
                    "");
        }
        else {
            notificationService.createAndSend(users,
                    "UPDATED",
                    "Progress updated for lesson"+lesson.getLessonTitle(),
                    "("+(int)completePercent+"% Completed",
                    "");
        }

        return lessonProgress1;

    }

    @Override
    @CacheEvict(value = {"progress"},allEntries = true)
    public LessonProgress updateDuration(LessonProgressDTO lessonProgressDTO) {
        Long userId = lessonProgressDTO.getUserId();
        Long lessonId = lessonProgressDTO.getLessonId();
        float playedTime = lessonProgressDTO.getPlayedTime();
        float duration = lessonProgressDTO.getDuration();

        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN") &&
                !Objects.equals(userService.getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized User");
        }

        Users users = userRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                ()->new RuntimeException("Lesson not found"));

        LessonProgress lessonProgress = lessonProgressRepository.findByUsersAndLesson(users,lesson).orElseThrow(
                ()->new RuntimeException("No progress"));

        lessonProgress.setDuration(duration);

        return lessonProgressRepository.save(lessonProgress);
    }

    @Override
    @Cacheable(value = "progress",key = "#userId")
    public float getProgress(Long userId, Long lessonId) {

        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN") &&
                !Objects.equals(userService.getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized User");
        }
        Users users = userRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                ()->new RuntimeException("Lesson not found"));

        LessonProgress lessonProgress = lessonProgressRepository.findByUsersAndLesson(users,lesson).orElseThrow(
                ()->new RuntimeException("No progress"));

        return lessonProgress.getPlayedTime();
    }
}
