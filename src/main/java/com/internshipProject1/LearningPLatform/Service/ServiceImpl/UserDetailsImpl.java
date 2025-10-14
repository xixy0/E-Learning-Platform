package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.CourseRegistrationDTO;
import com.internshipProject1.LearningPLatform.DTO.UserRegistrationDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Login;
import com.internshipProject1.LearningPLatform.Entity.StudentEnrollment;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.LoginRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsImpl implements UserService {
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
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Users updateUser(Long userId, UserRegistrationDTO userRegistrationDTO) {
        Users users = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));

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
        Login login = loginRepository.findById(loginId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
        login.setAccountStatus("INACTIVE");
        loginRepository.save(login);

    }

    @Override
    public void activateUser(Long loginId) {
        Login login = loginRepository.findById(loginId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
        login.setAccountStatus("ACTIVE");
        loginRepository.save(login);
    }



    @Override
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
    public Long getLoggedInUserId() {
        return getLoggedInUser().getUserId();
    }

    @Override
    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("Username not found"));

    }


    @Override
    public List<CourseRegistrationDTO> viewCourses(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("Username not found"));
        List<Course> course =user.getCourses();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(Course course1 : course){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(course1.getCourseTitle(),course1.getCourseDescription(),course1.getCourseCategory(),course1.getCourseDuration()));
        }
          return courseRegistrationDTOArrayList;
    }

    @Override
    public List<CourseRegistrationDTO> viewEnrolledCourses(Long userId) {

        Users users = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
        List<StudentEnrollment> enrollments =users.getStudentEnrollments();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(StudentEnrollment enrollment : enrollments){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(enrollment.getCourse().getCourseTitle(),enrollment.getCourse().getCourseDescription(),enrollment.getCourse().getCourseCategory(),enrollment.getCourse().getCourseDuration()));
        }
        return courseRegistrationDTOArrayList;
    }
}
