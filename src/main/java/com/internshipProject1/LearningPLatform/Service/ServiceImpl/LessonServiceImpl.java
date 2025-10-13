package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Lesson;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.LessonRepository;
import com.internshipProject1.LearningPLatform.Service.LessonService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LessonServiceImpl implements LessonService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserService userService;

    @Override
    public LessonDTO addLesson(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setLessonDescription(lessonDTO.getLessonDescription());
        lesson.setVideourl(lessonDTO.getVideoUrl());
        lesson.setPdfUrl(lessonDTO.getPdfUrl());
        lesson.setLessonTitle(lessonDTO.getLessonTitle());
        Course course = courseRepository.findByCourseTitle(lessonDTO.getCourseTitle()).orElseThrow(()->new RuntimeException("Course not found"));
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return lessonDTO;
    }

    @Override
    public LessonDTO updateLesson(Long lessonId, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("Lesson not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(lesson.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
        lesson.setLessonDescription(lessonDTO.getLessonDescription());
        lesson.setVideourl(lessonDTO.getVideoUrl());
        lesson.setPdfUrl(lessonDTO.getPdfUrl());
        lesson.setLessonTitle(lessonDTO.getLessonTitle());
        Course course = courseRepository.findByCourseTitle(lessonDTO.getCourseTitle()).orElseThrow(()->new RuntimeException("Course not found"));
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return lessonDTO;
    }

    @Override
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("Lesson not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(lesson.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
//        Course course = lesson.getCourse();
//        course.getLessons().remove(lesson);
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public List<LessonDTO> getAll() {
        List<Lesson> lessons = lessonRepository.findAll();
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        for(Lesson lesson : lessons){
            lessonDTOList.add(new LessonDTO(lesson.getLessonDescription(),
                    lesson.getLessonTitle(),lesson.getCourse().getCourseTitle(),
                    lesson.getVideourl(),
                    lesson.getPdfUrl()));
        }
        return lessonDTOList;
    }
}
