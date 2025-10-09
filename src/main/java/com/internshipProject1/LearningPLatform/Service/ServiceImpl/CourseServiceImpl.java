package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Course addCourse(Course course) {
        Users instructor = userRepository.findById(course.getInstructor().getUserId()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<Course> courses = instructor.getCourses();
        courses.add(course);
        instructor.setCourses(courses);
        userRepository.save(instructor);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long courseId, Course course) {
        Course courses = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));

        courses.setCourseTitle(course.getCourseTitle());
        courses.setCategory(course.getCategory());
        courses.setDuration(courses.getDuration());
        courses.setInstructor(course.getInstructor());
        courses.setDescription(courses.getDescription());

        return addCourse(courses);

    }

    @Override
    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found");
        }
        else{
            courseRepository.deleteById(courseId);
        }
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Users getInstructor(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        return course.getInstructor();
    }
}
