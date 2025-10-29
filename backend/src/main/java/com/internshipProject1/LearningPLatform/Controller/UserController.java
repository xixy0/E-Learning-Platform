package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.UserRegistrationDTO;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userRegistrationDTO));
        }catch(IllegalAccessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserRegistrationDTO userRegistrationDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId,userRegistrationDTO));

        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deactivate/{userId}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId){
        try{
            userService.deactivateUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User Deactivated");

        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/activate/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId){
        try{
            userService.activateUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User Activated");

        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/viewCoursesByAdmin/{userId}")
    public ResponseEntity<?> viewCoursesByAdmin(@PathVariable Long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.viewCoursesByAdmin(userId));
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/viewCourses")
    public ResponseEntity<?> viewCourses(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.viewCourses());
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/viewEnrolledCoursesForAdmin/{userId}")
    public ResponseEntity<?> viewEnrolledCoursesByAdmin(@PathVariable Long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.viewEnrolledCoursesByAdmin(userId));
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/viewEnrolledCourses")
    public ResponseEntity<?> viewEnrolledCourses(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.viewEnrolledCourses());
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getSubmissions/{userId}")
    public ResponseEntity<?> getSubmissionsByAdmin(@PathVariable Long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getSubmissionsByAdmin(userId));
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getSubmissions")
    public ResponseEntity<?> getSubmissions(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getSubmissions());
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/getAllStudentAssignmentSubmissions/{userId}")
    public ResponseEntity<?> getAllAssignmentSubmissionsByAdmin(@PathVariable Long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllStudentAssignmentSubmissionsByAdmin(userId));
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAllStudentAssignmentSubmissions")
    public ResponseEntity<?> getAllAssignmentSubmissions(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllStudentAssignmentSubmissions());
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getLoggedInUser")
    public ResponseEntity<?> getLoggedInUser(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getLoggedInUser());
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
