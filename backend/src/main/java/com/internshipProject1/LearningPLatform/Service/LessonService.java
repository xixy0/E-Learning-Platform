package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.Entity.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface LessonService {
    Lesson addLesson(Long courseId,LessonDTO lessonDTO);
    Lesson updateLesson(Long lessonId,LessonDTO lessonDTO);
    void deleteLesson(Long lessonId);
    List<LessonDTO> getAll();
    String uploadPdf(Long lessonId, MultipartFile file);
    LessonDTO getLessonById(Long lessonId);

}

