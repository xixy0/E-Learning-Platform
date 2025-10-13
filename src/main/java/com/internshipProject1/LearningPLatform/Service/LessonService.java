package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.Entity.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonService {
    LessonDTO addLesson(LessonDTO lessonDTO);
    LessonDTO updateLesson(Long lessonId,LessonDTO lessonDTO);
    void deleteLesson(Long lessonId);
    List<LessonDTO> getAll();

}
