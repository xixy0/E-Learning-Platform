package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.LessonProgressDTO;
import com.internshipProject1.LearningPLatform.Entity.LessonProgress;
import org.springframework.stereotype.Service;

@Service
public interface LessonProgressService {
    LessonProgress updateProgress(LessonProgressDTO lessonProgressDTO);
    LessonProgress updateDuration(LessonProgressDTO lessonProgressDTO);
    float getProgress(Long userId,Long lessonId);

}
