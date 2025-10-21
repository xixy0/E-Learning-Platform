package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.StudentEnrollmentDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.StudentEnrollment;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.StudentEnrollmentRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.StudentEnrollmentService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentEnrollmentServiceIMpl implements StudentEnrollmentService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    @CacheEvict(value = "enrollments",allEntries = true)
    public StudentEnrollment enroll(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        Users users = userService.getLoggedInUser();
        List<StudentEnrollment> studentEnrollments = users.getStudentEnrollments();
        for(StudentEnrollment studentEnrollment: studentEnrollments){
            if(Objects.equals(studentEnrollment.getCourse().getCourseId(), courseId)){
                throw new RuntimeException("Course already enrolled");
            }
        }
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setEnrollmentDate(LocalDate.now());
        studentEnrollment.setCourse(course);
        studentEnrollment.setUsers(userService.getLoggedInUser());
        return studentEnrollmentRepository.save(studentEnrollment);
    }

    @Override
    @CacheEvict(value = "enrollments",allEntries = true)
    public void unEnroll(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        Users users = userService.getLoggedInUser();
        List<StudentEnrollment> studentEnrollments = users.getStudentEnrollments();


        for(StudentEnrollment studentEnrollment : studentEnrollments) {
            if (Objects.equals(studentEnrollment.getCourse().getCourseId(), courseId)) {
                studentEnrollmentRepository.deleteById(studentEnrollment.getEnrollmentId());
                users.getStudentEnrollments().remove(studentEnrollment);
                userRepository.save(users);
                course.getStudentEnrollments().remove(studentEnrollment);
                courseRepository.save(course);
                break;
            }
        }
    }

    @Override
    @Cacheable(value = "enrollments",key = "'all'")
    public List<StudentEnrollmentDTO> getAll() {
        List<StudentEnrollment> studentEnrollments = studentEnrollmentRepository.findAll();
        List<StudentEnrollmentDTO> studentEnrollmentDTOList =new ArrayList<>();
        for(StudentEnrollment studentEnrollment:studentEnrollments){
            studentEnrollmentDTOList.add(new StudentEnrollmentDTO(studentEnrollment.getEnrollmentId(),
                    studentEnrollment.getEnrollmentDate(),studentEnrollment.getUsers().getUserId(),
                    studentEnrollment.getCourse().getCourseId()));
        }
        return studentEnrollmentDTOList;
    }

    @Override
    @Cacheable(value = "enrollments",key="#studentEnrollmentId")
    public StudentEnrollmentDTO getEnrollmentById(Long studentEnrollmentId) {
        StudentEnrollment studentEnrollment = studentEnrollmentRepository.findById(studentEnrollmentId).orElseThrow(
                ()->new RuntimeException("No enrollment"));
        return new StudentEnrollmentDTO(studentEnrollment.getEnrollmentId(),
                studentEnrollment.getEnrollmentDate(),studentEnrollment.getUsers().getUserId(),
                studentEnrollment.getCourse().getCourseId());
    }


//    @Override
//    public Double completionPercentage() {
//        return 0.0;
//    }
}
