package com.internshipProject1.LearningPLatform.Service;


import com.internshipProject1.LearningPLatform.DTO.CourseRegistrationDTO;
import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.DTO.UserDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
//import com.internshipProject1.LearningPLatform.Entity.Lesson;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
     Course addCourse(CourseRegistrationDTO courseRegistrationDTO);
     Course updateCourse(Long courseId,CourseRegistrationDTO courseRegistrationDTO);
     void deleteCourse(Long courseId);
     List<Course> getAll();
     UserDTO getInstructor(Long courseId);
     List<UserDTO> getStudentsEnrolled(Long courseId);
     List<LessonDTO> getLessons(Long courseId);
     //void removeEnrolledStudent(Long userId);
    //public void uploadCourseFiles(Long courseId);
    //public List<Course> trackCourseStats();
    //public List<Lesson> viewLessonByCourse(Long courseId);
}
