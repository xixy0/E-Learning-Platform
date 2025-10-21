package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.DTO.CourseRegistrationDTO;
import com.internshipProject1.LearningPLatform.DTO.UserDTO;
import com.internshipProject1.LearningPLatform.DTO.UserRegistrationDTO;
import com.internshipProject1.LearningPLatform.Entity.*;
import com.internshipProject1.LearningPLatform.Repository.LoginRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users addUser(UserRegistrationDTO userRegistrationDTO) throws IllegalAccessException {

        if(loginRepository.findByUsername(userRegistrationDTO.getUsername()).isPresent()){
                throw new IllegalAccessException("Username already exists");
        }

        Login login = new Login();
        login.setUsername(userRegistrationDTO.getUsername());
        login.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        login.setAccountStatus("ACTIVE");
        login.setRole(userRegistrationDTO.getRole());
        loginRepository.save(login);

        Users users = new Users();
        users.setLogin(login);
        users.setFirstName(userRegistrationDTO.getFirstName());
        users.setMiddleName(userRegistrationDTO.getMiddleName());
        users.setLastName(userRegistrationDTO.getLastName());
        users.setUserPhone(userRegistrationDTO.getPhoneNum());
        users.setUserDOB(userRegistrationDTO.getUserDOB());
        users.setAddress(userRegistrationDTO.getAddress());
        users.setEmail(userRegistrationDTO.getEmail());
        users.setGender(userRegistrationDTO.getGender());

        List<Course> courses = new ArrayList<>();
        users.setCourses(courses);

        List<StudentEnrollment> studentEnrollments= new ArrayList<>();
        users.setStudentEnrollments(studentEnrollments);

        return userRepository.save(users);

    }

    @Override
    @Cacheable(value = "users",key = "'all'")
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    @CacheEvict(value = {"users", "userCourses","userDTO"}, allEntries = true)
    public Users updateUser(Long userId, UserRegistrationDTO userRegistrationDTO) {
        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User does not exist"));
        if(!getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized user");
        }
        users.setFirstName(userRegistrationDTO.getFirstName());
        users.setMiddleName(userRegistrationDTO.getMiddleName());
        users.setLastName(userRegistrationDTO.getLastName());
        users.setUserPhone(userRegistrationDTO.getPhoneNum());
        users.setUserDOB(userRegistrationDTO.getUserDOB());
        users.setAddress(userRegistrationDTO.getAddress());
        users.setEmail(userRegistrationDTO.getEmail());
        users.setGender(userRegistrationDTO.getGender());

        return userRepository.save(users);
    }

    @Override
    public void deactivateUser(Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(()->new RuntimeException("User does not exist"));
        login.setAccountStatus("INACTIVE");
        loginRepository.save(login);

    }

    @Override
    public void activateUser(Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(()->new RuntimeException("User does not exist"));
        login.setAccountStatus("ACTIVE");
        loginRepository.save(login);
    }



    @Override
    @Cacheable(value = "users",key = "'loggedIn:' + T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public Users getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new RuntimeException("No authenticated user found");
        }
        String username = authentication.getName();
        Login login = loginRepository.findByUsername(username).get();
        return login.getUsers();
    }

    @Override
    @Cacheable(value = "userCourses", key = "#userId")
    public List<CourseRegistrationDTO> viewCourses(Long userId) {
        if(getLoggedInUser().getLogin().getRole().equalsIgnoreCase("INSTRUCTOR")
        && !Objects.equals(getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized instructor");
        }
        Users user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("Username not found"));
        List<Course> course =user.getCourses();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(Course course1 : course){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(course1.getCourseId(),course1.getCourseTitle(),course1.getCourseDescription(),course1.getCourseCategory(),course1.getCourseDuration(),
                    (course1.getInstructor().getFirstName()+" "+ course1.getInstructor().getMiddleName()+" "+course1.getInstructor().getLastName())));
        }
          return courseRegistrationDTOArrayList;
    }



    @Override
    @CacheEvict(value ={"users","userDTO","userCourses"},allEntries = true)
    public void deleteUser(Long userId) {
        if(userRepository.findById(userId).isEmpty()){
            throw new UsernameNotFoundException("User does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<AssignmentSubmissionDTO> getAllStudentAssignmentSubmissions() {
        Users users = getLoggedInUser();
        List<AssignmentSubmission> assignmentSubmissions =users.getAssignmentSubmissions();
        List<AssignmentSubmissionDTO> assignmentSubmissionDTOList = new ArrayList<>();
        for(AssignmentSubmission assignmentSubmission: assignmentSubmissions){
            assignmentSubmissionDTOList.add(new AssignmentSubmissionDTO(assignmentSubmission.getAssignmentSubmissionId(),
                    assignmentSubmission.getSubmissionDate(),assignmentSubmission.getAssignmentSubmissionUrl(),
                    assignmentSubmission.getUsers().getUserId(),
                    assignmentSubmission.getAssignment().getAssignmentId()));

        }
        return assignmentSubmissionDTOList;
    }

    @Override
    @Cacheable(value = "userDTO", key = "#userId")
    public UserDTO getUserById(Long userId) {
        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return new UserDTO(users.getFirstName(), users.getMiddleName(),users.getLastName(),
                users.getEmail(), users.getGender());
    }

    @Override
    @Cacheable(value = "userCourses", key = "'enrolled:'+#userId")
    public List<CourseRegistrationDTO> viewEnrolledCourses(Long userId) {
        if(getLoggedInUser().getLogin().getRole().equalsIgnoreCase("STUDENT")
                && !Objects.equals(getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized instructor");
        }
        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User does not exist"));
        List<StudentEnrollment> enrollments =users.getStudentEnrollments();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(StudentEnrollment enrollment : enrollments){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(enrollment.getCourse().getCourseId(), enrollment.getCourse().getCourseTitle(),
                    enrollment.getCourse().getCourseDescription(),enrollment.getCourse().getCourseCategory(),enrollment.getCourse().getCourseDuration(),
                    (enrollment.getCourse().getInstructor().getFirstName()+" "+enrollment.getCourse().getInstructor().getMiddleName()+" "+ enrollment.getCourse().getInstructor().getLastName())
                    ));
        }
        return courseRegistrationDTOArrayList;
    }
}
