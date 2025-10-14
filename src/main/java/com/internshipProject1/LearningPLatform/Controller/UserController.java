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
    public ResponseEntity<List<Users>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserRegistrationDTO userRegistrationDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId,userRegistrationDTO));

        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deactivate/{userId}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId){
        try{
            userService.deactivateUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User Deactivated");

        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/activate/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId){
        try{
            userService.activateUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User Activated");

        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/allEnrolledCourses")
    public ResponseEntity<?> viewAllEnrolledCourses(@PathVariable Long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.viewEnrolledCourses(userId));
        }
        catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}
