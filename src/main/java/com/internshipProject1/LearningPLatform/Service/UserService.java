package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.*;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
     Users addUser(UserRegistrationDTO userRegistrationDTO) throws IllegalAccessException;
     List<Users> getAll();
     Users updateUser(Long userId , UserRegistrationDTO userRegistrationDTO);
     void deactivateUser(Long userId);
     void activateUser(Long userId);
     UserDTO getLoggedInUser();
     List<CourseRegistrationDTO> viewEnrolledCourses(Long userId);
     List<CourseRegistrationDTO> viewCourses(Long userId);
     void deleteUser(Long userId);
     List<AssignmentSubmissionDTO> getAllStudentAssignmentSubmissions(Long userId);
     UserDTO getUserById(Long userId);
     List<SubmissionDTO> getSubmissions(Long userId);


}
