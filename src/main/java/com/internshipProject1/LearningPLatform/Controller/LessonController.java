package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.LessonDTO;
import com.internshipProject1.LearningPLatform.Service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/addLesson")
    public ResponseEntity<?> addLesson(@RequestBody LessonDTO lessonDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.addLesson(lessonDTO));
        }catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/updateLesson/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable Long lessonId,@RequestBody LessonDTO lessonDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lessonService.updateLesson(lessonId,lessonDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteLesson/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId){
        try{
            lessonService.deleteLesson(lessonId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAll(){
     return  ResponseEntity.status(HttpStatus.OK).body(lessonService.getAll());
    }

    @PostMapping("/uploadPdf/{lessonId}")
    public ResponseEntity<?> uploadPdf(@PathVariable Long lessonId, @RequestBody MultipartFile file){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(lessonService.uploadPdf(lessonId,file));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }



}
