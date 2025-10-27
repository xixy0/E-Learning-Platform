package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.LessonProgressDTO;
import com.internshipProject1.LearningPLatform.Service.ServiceImpl.LessonProgressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lessonProgress")
public class LessonProgressController {

    @Autowired
    private LessonProgressServiceImpl lessonProgressServiceImpl;

    @PostMapping("/updateLessonProgress")
    public ResponseEntity<?> updateLessonProgress(@RequestBody LessonProgressDTO lessonProgressDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lessonProgressServiceImpl.updateProgress(lessonProgressDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/updateDuration")
    public ResponseEntity<?> updateDuration(@RequestBody LessonProgressDTO lessonProgressDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lessonProgressServiceImpl.updateDuration(lessonProgressDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getLessonProgress/{userId}/{lessonId}")
    public ResponseEntity<?> getLessonProgress(@PathVariable Long userId,@PathVariable Long lessonId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lessonProgressServiceImpl.getProgress(userId,lessonId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
