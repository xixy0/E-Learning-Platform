package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assignmentSubmissions")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;

    @PostMapping("/addAssignmentSubmission/{assignmentId}")
    public ResponseEntity<?> addAssignment(@PathVariable Long assignmentId ,@ModelAttribute AssignmentSubmissionDTO assignmentSubmissionDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(assignmentSubmissionService.addAssignmentSubmission(assignmentId,assignmentSubmissionDTO));
        }catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteAssignmentSubmission/{assignmentSubmissionId}")
    public ResponseEntity<?> deleteAssignmentSubmission(@PathVariable Long assignmentSubmissionId){
        try{
            assignmentSubmissionService.deleteAssignmentSubmission(assignmentSubmissionId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAll(){
        return  ResponseEntity.status(HttpStatus.OK).body(assignmentSubmissionService.getAll());
    }


}
