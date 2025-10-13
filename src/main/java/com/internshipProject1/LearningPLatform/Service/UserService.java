package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.CourseRegistrationDTO;
import com.internshipProject1.LearningPLatform.DTO.UserRegistrationDTO;
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
     Users getLoggedInUser();
     Long getLoggedInUserId();
     Users getUserById(Long userId);
     List<CourseRegistrationDTO> viewEnrolledCourses(Long userId);
     List<CourseRegistrationDTO> viewCourses(Long userId);



}
