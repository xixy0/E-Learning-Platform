package com.internshipProject1.LearningPLatform.Service;

import com.internshipProject1.LearningPLatform.DTO.UserRegistrationDTO;
//import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public Users addUser(UserRegistrationDTO userRegistrationDTO) throws IllegalAccessException;
    public List<Users> getAll();
    public Users updateUser(Long userId , UserRegistrationDTO userRegistrationDTO);
    public void deactivateUser(Long userId);
    public void activateUser(Long userId);
    //public List<StudentEnrollment> viewEnrolledCourses(Long userId);
    public List<Course> viewCourses(Long userId);


}
