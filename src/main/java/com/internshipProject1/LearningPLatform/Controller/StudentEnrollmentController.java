package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.Service.StudentEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/student")
public class StudentEnrollmentController {

    @Autowired
    private StudentEnrollmentService studentEnrollmentService;

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enroll(@PathVariable Long courseId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentEnrollmentService.enroll(courseId));
        }catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/unenroll/{courseId}")
    public ResponseEntity<?> unEnroll(@PathVariable Long courseId){

        try{
            studentEnrollmentService.unEnroll(courseId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Unrolled");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not enrolled");
        }
    }


}
