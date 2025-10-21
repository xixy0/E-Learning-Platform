package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.*;
import com.internshipProject1.LearningPLatform.Entity.*;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.StudentEnrollmentRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.CourseService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"courses","courseLesson","courseQuiz","courseAssignment"},allEntries = true)
    public Course addCourse(CourseRegistrationDTO courseRegistrationDTO) {
      Course course = new Course();
      course.setCourseTitle(courseRegistrationDTO.getCourseTitle());
      course.setCourseDescription(courseRegistrationDTO.getCourseDescription());
      course.setCourseCategory(courseRegistrationDTO.getCourseCategory());
      course.setCourseDuration(courseRegistrationDTO.getCourseDuration());

      Users instructor = userService.getLoggedInUser();
      course.setInstructor(instructor);

      course.setStudentEnrollments(new ArrayList<>());
      course.setQuiz(new ArrayList<>());
      course.setAssignments(new ArrayList<>());

      return courseRepository.save(course);


    }

    @Override
    @CacheEvict(value = {"courses","courseLesson","courseQuiz","courseAssignment"},allEntries = true)
    public Course updateCourse(Long courseId, CourseRegistrationDTO courseRegistrationDTO) {
        Course courses = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(courses.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
           throw new RuntimeException("Unauthorized Instructor");
        }
        courses.setCourseTitle(courseRegistrationDTO.getCourseTitle());
        courses.setCourseCategory(courseRegistrationDTO.getCourseCategory());
        courses.setCourseDuration(courseRegistrationDTO.getCourseDuration());
        courses.setCourseDescription(courseRegistrationDTO.getCourseDescription());

        return courseRepository.save(courses);
    }

    @Override
    @CacheEvict(value = {"courses","courseLesson","courseQuiz","courseAssignment"},allEntries = true)
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
    @Cacheable(value = "courses",key = "'all'")
    public List<CourseRegistrationDTO> getAll() {

        List<Course> courses =  courseRepository.findAll();
        List<CourseRegistrationDTO> courseRegistrationDTOList = new ArrayList<>();

        for(Course course : courses){
            courseRegistrationDTOList.add(new CourseRegistrationDTO(
                    course.getCourseId(),
                    course.getCourseTitle(),
                    course.getCourseDescription(),
                    course.getCourseCategory(),
                    course.getCourseDuration(),
                    (course.getInstructor().getFirstName()+" "+ course.getInstructor().getMiddleName()+" "+course.getInstructor().getLastName()),
                    course.getStudentEnrollments().size()));
        }
        return courseRegistrationDTOList;
    }

    @Override
    @Cacheable(value = "courseInstructor",key = "#courseId")
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
            userDTOList.add(new UserDTO(
                    studentEnrollment.getUsers().getFirstName(),
                    studentEnrollment.getUsers().getMiddleName(),
                    studentEnrollment.getUsers().getLastName(),
                    studentEnrollment.getUsers().getEmail(),
                    studentEnrollment.getUsers().getGender())
                    );
        }
        return userDTOList;
    }

    @Override
    @Cacheable(value = "courseLesson",key = "#courseId")
    public List<LessonDTO> getLessons(Long courseId) {
        List<Lesson> lessons =  courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not founnd")).getLessons();
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

//    @Override
//    public void removeEnrolledStudent(Long courseId ,Long userId) {
//        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
//        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
//        List<StudentEnrollment> studentEnrollments = course.getStudentEnrollments();
//        if(studentEnrollments.isEmpty()) throw new RuntimeException("Student not enrolled");
//        for(StudentEnrollment studentEnrollment : studentEnrollments){
//            if(studentEnrollment.getUsers().getUserId() == userId){
//                studentEnrollments.remove(studentEnrollment);
//                users.getStudentEnrollments().remove(studentEnrollment);
//                studentEnrollmentRepository.delete(studentEnrollment);
//            }
//        }
//
//    }

    @Override
    @Cacheable(value = "courseQuiz", key="#courseId")
    public List<Quiz> getAllQuiz(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        return course.getQuiz();
    }

    @Override
    @Cacheable(value = "courseAssignment",key = "#courseId")
    public List<AssignmentDTO> getAllAssignments(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        List<Assignment> assignments =  course.getAssignments();
        List<AssignmentDTO> assignmentDTOList = new ArrayList<>();
        for(Assignment assignment:assignments){
          assignmentDTOList.add(new AssignmentDTO(
                  assignment.getAssignmentId(),
                  assignment.getAssignmentTitle(),
                  assignment.getAssignmentDescription(),
                  assignment.getCourse().getCourseId(),
                  assignment.getAssignmentPdfUrl()
          ));
        }
        return assignmentDTOList;
    }

    @Override
    @Cacheable(value = "courses", key = "#courseId")
    public CourseRegistrationDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        return new CourseRegistrationDTO(
                course.getCourseId(),
                course.getCourseTitle(),
                course.getCourseDescription(),
                course.getCourseCategory(),
                course.getCourseDuration(),
                (course.getInstructor().getFirstName()+" "+ course.getInstructor().getMiddleName()+" "+course.getInstructor().getLastName()),
                course.getStudentEnrollments().size());
    }
}
