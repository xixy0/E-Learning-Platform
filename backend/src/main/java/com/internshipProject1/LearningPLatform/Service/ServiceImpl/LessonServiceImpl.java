package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Lesson;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.LessonRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.LessonService;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @CacheEvict(value = {"lessons","courseLesson"},allEntries = true,beforeInvocation = true)
    public Lesson addLesson(Long courseId ,LessonDTO lessonDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();
        if(!users.getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        Lesson lesson = new Lesson();
        lesson.setLessonDescription(lessonDTO.getLessonDescription());
        lesson.setVideourl(lessonDTO.getVideoUrl());
        lesson.setPdfUrl(" ");
        lesson.setLessonTitle(lessonDTO.getLessonTitle());
        lesson.setCourse(course);
        lesson.setInstructorName(course.getInstructor().getFirstName()+" "+course.getInstructor().getMiddleName() + " " + course.getInstructor().getLastName());
        Lesson lesson1 =  lessonRepository.save(lesson);
        if (lessonDTO.getFile() != null && !lessonDTO.getFile().isEmpty()) {
            String pdfUrl = uploadPdf(lesson1.getLessonId(), lessonDTO.getFile());
            lesson1.setPdfUrl(pdfUrl);
            lessonRepository.save(lesson1);
        }
        notificationService.createAndSend(users,
                "LESSON_CREATED",
                "Title: "+lesson.getLessonTitle(),
                "Lesson created",
                "Description: "+ lesson.getLessonDescription() +" Instructor: "+ lesson.getInstructorName());

        return lesson1;
    }

    @Override
    @CacheEvict(value = {"lessons","courseLesson"},allEntries = true,beforeInvocation = true)
    public Lesson updateLesson(Long lessonId, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("Lesson not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();

        if(!users.getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(lesson.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }
        if(!lessonDTO.getLessonDescription().isEmpty()) {
            lesson.setLessonDescription(lessonDTO.getLessonDescription());
        }

        if(!lessonDTO.getVideoUrl().isEmpty()) {
            lesson.setVideourl(lessonDTO.getVideoUrl());
        }

        if(!lessonDTO.getLessonTitle().isEmpty()) {
            lesson.setLessonTitle(lessonDTO.getLessonTitle());
        }

        if (lessonDTO.getFile() != null && !lessonDTO.getFile().isEmpty()) {
            String pdfUrl = uploadPdf(lessonId, lessonDTO.getFile());
            lesson.setPdfUrl(pdfUrl);
        }

//        Course course = courseRepository.findById(lessonDTO.getCourseId()).orElseThrow(()->new RuntimeException("Course not found"));
//        lesson.setCourse(course);
//        lesson.setInstructorName(course.getInstructor().getFirstName()+" "+course.getInstructor().getMiddleName() + " " + course.getInstructor().getLastName());

        notificationService.createAndSend(users,
                "LESSON_UPDATED",
                "Title: "+lesson.getLessonTitle(),
                "Lesson updated",
                "Description: "+ lesson.getLessonDescription() +" Instructor: "+ lesson.getInstructorName());

        return lessonRepository.save(lesson);
    }

    @Override
    @CacheEvict(value = {"lessons","courseLesson"},allEntries = true,beforeInvocation = true)
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("Lesson not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(lesson.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId()))
        {
            throw new RuntimeException("Unauthorized Instructor");
        }

        lessonRepository.deleteById(lessonId);
    }

    @Override
    @Cacheable(value = "lessons",key="'all'")
    public List<LessonDTO> getAll() {
       List<Lesson> lessons = lessonRepository.findAll();
       List<LessonDTO> lessonDTOList = new ArrayList<>();

       for(Lesson lesson : lessons){
           lessonDTOList.add(new LessonDTO(
                   lesson.getCourse().getCourseId(),
                   lesson.getLessonId(),
                   lesson.getLessonTitle(),
                   lesson.getLessonDescription(),
                   lesson.getVideourl(),
                   lesson.getPdfUrl(),
                   (lesson.getCourse().getInstructor().getFirstName()+" "+ lesson.getCourse().getInstructor().getMiddleName()+" "+lesson.getCourse().getInstructor().getLastName())));
       }
        return lessonDTOList;
    }

    @Override
    public String uploadPdf(Long lessonId, MultipartFile file) {
        final String UPLOAD_DIR = "/lessons/pdfs/";
        try {
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


            return "/lessons/pdfs/" + safeFilename;

        }catch (IOException ex){
            throw  new RuntimeException("Upload failed");
        }
    }

    @Override
    @Cacheable(value = "lessons",key="#lessonId")
    public LessonDTO getLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()-> new RuntimeException("Lesson not found"));

        return new LessonDTO(
                lesson.getCourse().getCourseId(),
                lesson.getLessonId(),
                lesson.getLessonTitle(),
                lesson.getLessonDescription(),
                lesson.getVideourl(),
                lesson.getPdfUrl(),
                (lesson.getCourse().getInstructor().getFirstName()+" "+ lesson.getCourse().getInstructor().getMiddleName()+" "+lesson.getCourse().getInstructor().getLastName()));
    }



}
