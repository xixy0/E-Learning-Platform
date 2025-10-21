package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.CourseRegistrationDTO;
import com.internshipProject1.LearningPLatform.DTO.UserDTO;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourse(@RequestBody CourseRegistrationDTO courseRegistrationDTO){
         return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseRegistrationDTO));
    }

    @PostMapping("/updateCourse/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId,@RequestBody CourseRegistrationDTO courseRegistrationDTO){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseService.updateCourse(courseId,courseRegistrationDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId){
        try{
            courseService.deleteCourse(courseId);
            return ResponseEntity.status(HttpStatus.OK).body("Course removed successfully");
        }catch (RuntimeException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll());
    }

    @GetMapping("/getInstructor/{courseId}")
    public ResponseEntity<?> getInstructor(@PathVariable Long courseId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getInstructor(courseId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/enrollments/{courseId}")
    public ResponseEntity<List<?>> getStudentsEnrolled(@PathVariable Long courseId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getStudentsEnrolled(courseId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }

    @GetMapping("/getLessons/{courseId}")
    public ResponseEntity<List<?>> getLessons(@PathVariable Long courseId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getLessons(courseId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }


    @GetMapping("/getAllQuiz/{courseId}")
    public ResponseEntity<List<?>> getAllQuiz(@PathVariable Long courseId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseService.getAllQuiz(courseId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }


    @GetMapping("/getAllAssignments/{courseId}")
    public ResponseEntity<List<?>> getAllAssignments(@PathVariable Long courseId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseService.getAllAssignments(courseId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }


}
