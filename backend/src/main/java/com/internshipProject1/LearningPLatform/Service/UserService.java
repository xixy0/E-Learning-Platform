package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.*;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
     Users addUser(UserRegistrationDTO userRegistrationDTO) throws IllegalAccessException;
     List<UserDTO> getAll();
     Users updateUser(Long userId , UserRegistrationDTO userRegistrationDTO);
     void deactivateUser(Long userId);
     void activateUser(Long userId);
     void deleteUser(Long userId);
     UserDTO getLoggedInUser();
     UserDTO getUserById(Long userId);
     List<AssignmentSubmissionDTO> getAllStudentAssignmentSubmissionsByAdmin(Long userId);
     List<AssignmentSubmissionDTO> getAllStudentAssignmentSubmissions();
     List<CourseRegistrationDTO> viewEnrolledCoursesByAdmin(Long userId);
     List<CourseRegistrationDTO> viewEnrolledCourses();
     List<CourseRegistrationDTO> viewCoursesByAdmin(Long userId);
     List<CourseRegistrationDTO> viewCourses();
     List<SubmissionDTO> getSubmissionsByAdmin(Long userId);
     List<SubmissionDTO> getSubmissions();





}
