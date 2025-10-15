package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.CourseRegistrationDTO;
import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.DTO.UserDTO;
import com.internshipProject1.LearningPLatform.Entity.*;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.StudentEnrollmentRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.CourseService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Autowired
    private UserService userService;



    @Override
    public Course addCourse(CourseRegistrationDTO courseRegistrationDTO) {
      Course course = new Course();
      course.setCourseTitle(courseRegistrationDTO.getCourseTitle());
      course.setCourseDescription(courseRegistrationDTO.getCourseDescription());
      course.setCourseCategory(courseRegistrationDTO.getCourseCategory());
      course.setCourseDuration(courseRegistrationDTO.getCourseDuration());
      Users instructor = userService.getLoggedInUser();
      course.setInstructor(instructor);
      course.setStudentEnrollments(new ArrayList<>());
      List<Quiz> quizzes = new ArrayList<>();
      course.setQuiz(quizzes);
      return courseRepository.save(course);


    }

    @Override
    public Course updateCourse(Long courseId, CourseRegistrationDTO courseRegistrationDTO) {
        Course courses = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(courses.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
           throw new RuntimeException("Unauthorized Instructor");
        }
        courses.setCourseTitle(courseRegistrationDTO.getCourseTitle());
        courses.setCourseCategory(courseRegistrationDTO.getCourseCategory());
        courses.setCourseDuration(courseRegistrationDTO.getCourseDuration());
        courses.setInstructor(courses.getInstructor());
        courses.setCourseDescription(courseRegistrationDTO.getCourseDescription());

        return courseRepository.save(courses);
    }

    @Override
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->  new RuntimeException("Course not found"));
        if(userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")){
            courseRepository.deleteById(courseId);
        }
        else{
            Long instructorId = (course.getInstructor() != null) ? course.getInstructor().getUserId() : null;
            Long loggedInUserId = userService.getLoggedInUser().getUserId();
            if(instructorId == null || !instructorId.equals(loggedInUserId)){
                throw new RuntimeException("Unauthorized Instructor");
            }
            courseRepository.deleteById(courseId);
        }
        }


    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public UserDTO getInstructor(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        return new UserDTO(
                course.getInstructor().getFirstName(),
                course.getInstructor().getMiddleName(),
                course.getInstructor().getLastName(),
                course.getInstructor().getEmail(),
                course.getInstructor().getGender()
        );
    }

    @Override
    public List<UserDTO> getStudentsEnrolled(Long courseId) {


        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
         if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        List<StudentEnrollment> studentEnrollments = course.getStudentEnrollments();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(StudentEnrollment studentEnrollment:studentEnrollments){
            userDTOList.add(new UserDTO(studentEnrollment.getUsers().getFirstName(),
                    studentEnrollment.getUsers().getMiddleName(),
                    studentEnrollment.getUsers().getLastName(),
                    studentEnrollment.getUsers().getEmail(),
                    studentEnrollment.getUsers().getGender())
                    );
        }
        return userDTOList;
    }

    @Override
    public List<LessonDTO> getLessons(Long courseId) {
        List<Lesson> lessons =  courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not founnd")).getLessons();
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        for(Lesson lesson : lessons){
            lessonDTOList.add(new LessonDTO(lesson.getLessonDescription(),
                    lesson.getLessonTitle(),
                    lesson.getCourse().getCourseTitle(),
                    lesson.getVideourl(),
                    lesson.getPdfUrl(),
                    lesson.getCourse().getInstructor().getFirstName()));
        }
        return lessonDTOList;
    }

    @Override
    public void removeEnrolledStudent(Long courseId ,Long userId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        List<StudentEnrollment> studentEnrollments = course.getStudentEnrollments();
        if(studentEnrollments.isEmpty()) throw new RuntimeException("Student not enrolled");
        for(StudentEnrollment studentEnrollment : studentEnrollments){
            if(studentEnrollment.getUsers().getUserId() == userId){
                studentEnrollments.remove(studentEnrollment);
                users.getStudentEnrollments().remove(studentEnrollment);
                studentEnrollmentRepository.delete(studentEnrollment);
            }
        }

    }

    @Override
    public List<QuizDTO> getAllQuiz(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        List<Quiz> quizzes = course.getQuiz();
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for(Quiz quiz:quizzes){
            quizDTOList.add(new QuizDTO(quiz.getQuizTitle(),quiz.getTotalMarks(),
                    quiz.getCourse().getCourseTitle(),quiz.getTimestamp()));
        }
        return quizDTOList;
    }
}
