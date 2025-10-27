package com.internshipProject1.LearningPLatform.Controller;


import com.internshipProject1.LearningPLatform.DTO.SubmissionDTO;
import com.internshipProject1.LearningPLatform.Service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/addSubmission")
    public ResponseEntity<?> addQuiz(@RequestBody SubmissionDTO submissionDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(submissionService.addSubmission(submissionDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteSubmission/{submissionId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long submissionId){
        try{
            submissionService.deleteSubmission(submissionId);
            return ResponseEntity.status(HttpStatus.OK).body("Submission removed");
        }catch (RuntimeException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(submissionService.getAll());
    }

    @GetMapping("/getSubmissionById/{submissionId}")
    public ResponseEntity<?> getSubmissionById(@PathVariable Long submissionId) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(submissionService.getSubmissionById(submissionId));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }
}
