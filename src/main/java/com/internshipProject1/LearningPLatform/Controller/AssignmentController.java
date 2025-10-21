package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.AssignmentDTO;
import com.internshipProject1.LearningPLatform.Service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/addAssignment/{courseId}")
    public ResponseEntity<?> addAssignment(@PathVariable Long courseId,@RequestBody AssignmentDTO assignmentDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.addAssignment(courseId,assignmentDTO));
        }catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/updateAssignment/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long assignmentId, @RequestBody AssignmentDTO assignmentDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(assignmentService.updateAssignment(assignmentId,assignmentDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteAssignment/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long assignmentId){
        try{
            assignmentService.deleteAssignment(assignmentId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAll(){
        return  ResponseEntity.status(HttpStatus.OK).body(assignmentService.getAll());
    }

    @PostMapping("/uploadAssignmentPdf/{assignmentId}")
    public ResponseEntity<?> uploadPdf(@PathVariable Long assignmentId, @RequestBody MultipartFile file){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(assignmentService.uploadAssignmentPdf(assignmentId,file));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteAssignmentUrl/{assignmentId}")// not tested
    public ResponseEntity<?> deleteAssignmentUrl(@PathVariable Long assignmentId,@RequestParam String path){
        try{
            assignmentService.removeAssignmentPdf(assignmentId,path);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted pdf successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAssignmentSubmissions/{assignmentId}")
    public ResponseEntity<?> getAssignmentSubmissions(@PathVariable Long assignmentId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(assignmentService.getAllCourseAssignmentSubmissions(assignmentId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
