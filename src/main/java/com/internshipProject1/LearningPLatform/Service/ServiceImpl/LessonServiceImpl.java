package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Lesson;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.LessonRepository;
import com.internshipProject1.LearningPLatform.Service.LessonService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserService userService;

    @Override
    public LessonDTO addLesson(LessonDTO lessonDTO) {
        Course course = courseRepository.findByCourseTitle(lessonDTO.getCourseTitle()).orElseThrow(()->new RuntimeException("Course not found"));
        System.out.println("addLesson");
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        System.out.println("addLesson");

        Lesson lesson = new Lesson();
        lesson.setLessonDescription(lessonDTO.getLessonDescription());
        lesson.setVideourl(lessonDTO.getVideoUrl());
        lesson.setPdfUrl(lessonDTO.getPdfUrl());
        lesson.setLessonTitle(lessonDTO.getLessonTitle());
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

        lessonRepository.deleteById(lessonId);
    }

    @Override
    public List<Lesson> getAll() {
       return lessonRepository.findAll();
    }

    @Override
    public String uploadPdf(Long lessonId, MultipartFile file) {
        final String UPLOAD_DIR = "/uploads/pdfs/";
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(lesson.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        try{

            if (file.isEmpty()) {
                return "File is empty!";
            }
                String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "pdfs";
                File directory = new File(uploadDir);
                if (!directory.exists()) directory.mkdirs();

                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }

                String safeFilename = "lesson_" + lessonId + "_" + System.currentTimeMillis() + extension;
                File destinationFile = new File(directory, safeFilename);
                file.transferTo(destinationFile);

                String relativePath = "/uploads/pdfs/" + safeFilename;
                lesson.setPdfUrl(relativePath);
                lessonRepository.save(lesson);
                return relativePath;

        }catch (IOException ex){
            throw  new RuntimeException("Upload failed");
        }
    }
}
