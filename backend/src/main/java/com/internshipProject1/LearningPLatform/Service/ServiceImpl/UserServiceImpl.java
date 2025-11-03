package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.*;
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
    @CacheEvict(value = {"users", "userCourses","userDTO","userEnrollment","userSubmissions"}, allEntries = true)
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

        users.setCourses(new ArrayList<>());
        users.setStudentEnrollments(new ArrayList<>());
        users.setSubmissions(new ArrayList<>());
        users.setAssignmentSubmissions(new ArrayList<>());

       return userRepository.save(users);
    }

    @Override
    @Cacheable(value = "users",key = "'all'")
    public List<UserDTO> getAll() {
        List<Users> users =  userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for(Users user : users){
            userDTOList.add(new UserDTO(
                    user.getUserId(),
                    user.getLogin().getLoginId(),
                    user.getLogin().getRole(),
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getEmail()
            ));
        }
        return userDTOList;
    }

    @Override
    @CacheEvict(value = {"users", "userCourses","userDTO","courses","userEnrollment","userSubmissions"}, allEntries = true)
    public Users updateUser(Long userId, UserRegistrationDTO userRegistrationDTO) {
        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User does not exist"));
        if(!getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(getLoggedInUser().getUserId(),userId)){
            throw new RuntimeException("Unauthorized user");
        }
        if(!userRegistrationDTO.getFirstName().isEmpty()) {
            users.setFirstName(userRegistrationDTO.getFirstName());
        }

        if(!userRegistrationDTO.getMiddleName().isEmpty()) {
            users.setMiddleName(userRegistrationDTO.getMiddleName());
        }

        if(!userRegistrationDTO.getLastName().isEmpty()){
        users.setLastName(userRegistrationDTO.getLastName());
        }

        if(!userRegistrationDTO.getPhoneNum().isEmpty()) {
            users.setUserPhone(userRegistrationDTO.getPhoneNum());
        }

        if(userRegistrationDTO.getUserDOB()!=null) {
            users.setUserDOB(userRegistrationDTO.getUserDOB());
        }

        if(!userRegistrationDTO.getAddress().isEmpty()) {
            users.setAddress(userRegistrationDTO.getAddress());
        }

        if(!userRegistrationDTO.getEmail().isEmpty()) {
            users.setEmail(userRegistrationDTO.getEmail());
        }

        if(!userRegistrationDTO.getGender().isEmpty()) {
            users.setGender(userRegistrationDTO.getGender());
        }
        return userRepository.save(users);

    }

    @Override
    @CacheEvict(value ={"users","userDTO","userCourses","courses","userSubmissions","userEnrollment"},allEntries = true)
    public void deactivateUser(Long loginId) {
        if(!getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized user");
        }
        Login login = loginRepository.findById(loginId).orElseThrow(()->new RuntimeException("User does not exist"));
        login.setAccountStatus("INACTIVE");
        loginRepository.save(login);

    }

    @Override
    @CacheEvict(value ={"users","userDTO","userCourses","courses","userSubmissions","userEnrollment"},allEntries = true)
    public void activateUser(Long loginId) {
        if(!getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized user");
        }
        Login login = loginRepository.findById(loginId).orElseThrow(()->new RuntimeException("User does not exist"));
        login.setAccountStatus("ACTIVE");
        loginRepository.save(login);
    }



    @Override
    @Cacheable(value = "users",key = "#root.methodName")
    public UserDTO getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new RuntimeException("No authenticated user found");
        }
        String username = authentication.getName();
        System.out.println("username: " +username);
        Login login = loginRepository.findByUsername(username).get();
        Users users = login.getUsers();
        return new UserDTO(
                users.getUserId(),
                login.getLoginId(),
                login.getRole(),
                users.getFirstName(),
                users.getMiddleName(),
                users.getLastName(),
                users.getEmail()
        );
    }

    @Override
    @Cacheable(value = "userCourses", key = "#userId")
    public List<CourseRegistrationDTO> viewCoursesByAdmin(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("Username not found"));
        List<Course> course =user.getCourses();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(Course course1 : course){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(
                    course1.getCourseId(),
                    course1.getCourseTitle(),
                    course1.getCourseDescription(),
                    course1.getCourseCategory(),
                    (course1.getInstructor().getFirstName()+" "+ course1.getInstructor().getMiddleName()+" "+course1.getInstructor().getLastName())));
        }
          return courseRegistrationDTOArrayList;
    }

    @Override
    @Cacheable(value = "userCourses", key = "viewCourses")
    public List<CourseRegistrationDTO> viewCourses() {
        Users user = userRepository.findById(getLoggedInUser().getUserId()).orElseThrow(()->new RuntimeException("Username not found"));
        List<Course> course =user.getCourses();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(Course course1 : course){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(
                    course1.getCourseId(),
                    course1.getCourseTitle(),
                    course1.getCourseDescription(),
                    course1.getCourseCategory(),
                    (course1.getInstructor().getFirstName()+" "+ course1.getInstructor().getMiddleName()+" "+course1.getInstructor().getLastName())));
        }
        return courseRegistrationDTOArrayList;
    }


    @Override
    @CacheEvict(value ={"users","userDTO","userCourses","courses","userSubmissions","userEnrollment"},allEntries = true)
    public void deleteUser(Long userId) {
        if(userRepository.findById(userId).isEmpty()){
            throw new UsernameNotFoundException("User does not exist");
        }
        if(!getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized user");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<AssignmentSubmissionDTO> getAllStudentAssignmentSubmissionsByAdmin(Long userId) {

        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("Username not found"));

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
    public List<AssignmentSubmissionDTO> getAllStudentAssignmentSubmissions() {

        Users users = userRepository.findById(getLoggedInUser().getUserId()).orElseThrow(()->new RuntimeException("Username not found"));

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
        if(!getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Unauthorized user");
        }
        Users users = userRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("User not found"));
        return new UserDTO(
                users.getUserId(),
                users.getLogin().getLoginId(),
                users.getLogin().getRole(),
                users.getFirstName(),
                users.getMiddleName(),
                users.getLastName(),
                users.getEmail());
    }


    @Override
    @Cacheable(value = "userSubmissions",key="#userId")
    public List<SubmissionDTO> getSubmissionsByAdmin(Long userId) {

        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("Username not found"));
        List<Submission> submissions = users.getSubmissions();
        List<SubmissionDTO> submissionDTOList = new ArrayList<>();
        for(Submission submission:submissions){
            submissionDTOList.add(new SubmissionDTO(
                    submission.getSubmissionId(),
                    submission.getQuiz().getQuizId(),
                    submission.getStudent().getUserId(),
                    submission.getScore(),
                    submission.getTimestamp(),
                    submission.getAnswers()));
        }
        return submissionDTOList;
    }

    @Override
    @Cacheable(value = "userSubmissions",key="submissions")
    public List<SubmissionDTO> getSubmissions() {

        Users users = userRepository.findById(getLoggedInUser().getUserId()).orElseThrow(()->new RuntimeException("Username not found"));
        List<Submission> submissions = users.getSubmissions();
        List<SubmissionDTO> submissionDTOList = new ArrayList<>();
        for(Submission submission:submissions){
            submissionDTOList.add(new SubmissionDTO(
                    submission.getSubmissionId(),
                    submission.getQuiz().getQuizId(),
                    submission.getStudent().getUserId(),
                    submission.getScore(),
                    submission.getTimestamp(),
                    submission.getAnswers()));
        }
        return submissionDTOList;
    }

    @Override
    @Cacheable(value = "userEnrollment", key = "'enrolled:'+#userId")
    public List<CourseRegistrationDTO> viewEnrolledCoursesByAdmin(Long userId) {

        Users users = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User does not exist"));
        List<StudentEnrollment> enrollments =users.getStudentEnrollments();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(StudentEnrollment enrollment : enrollments){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(
                    enrollment.getCourse().getCourseId(),
                    enrollment.getCourse().getCourseTitle(),
                    enrollment.getCourse().getCourseDescription(),
                    enrollment.getCourse().getCourseCategory(),
                    (enrollment.getCourse().getInstructor().getFirstName()+" "+enrollment.getCourse().getInstructor().getMiddleName()+" "+ enrollment.getCourse().getInstructor().getLastName())
                    ));
        }
        return courseRegistrationDTOArrayList;
    }

    @Override
    @Cacheable(value = "userEnrollment", key = "'studentEnrollment'")
    public List<CourseRegistrationDTO> viewEnrolledCourses() {

        Users users = userRepository.findById(getLoggedInUser().getUserId()).orElseThrow(()->new RuntimeException("User does not exist"));
        List<StudentEnrollment> enrollments =users.getStudentEnrollments();
        List<CourseRegistrationDTO> courseRegistrationDTOArrayList = new ArrayList<>();
        for(StudentEnrollment enrollment : enrollments){
            courseRegistrationDTOArrayList.add(new CourseRegistrationDTO(
                    enrollment.getCourse().getCourseId(),
                    enrollment.getCourse().getCourseTitle(),
                    enrollment.getCourse().getCourseDescription(),
                    enrollment.getCourse().getCourseCategory(),
                    (enrollment.getCourse().getInstructor().getFirstName()+" "+enrollment.getCourse().getInstructor().getMiddleName()+" "+ enrollment.getCourse().getInstructor().getLastName())
            ));
        }
        return courseRegistrationDTOArrayList;
    }

}
