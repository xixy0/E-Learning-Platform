package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.QuestionDTO;
import com.internshipProject1.LearningPLatform.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @PostMapping("/addQuestion")
    public ResponseEntity<?> addQuiz(@RequestBody QuestionDTO questionDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addQuestion(questionDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/updateQuestion/{questionId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long questionId, @RequestBody QuestionDTO questionDTO){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(questionService.updateQuestion(questionId,questionDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteQuestion/{questionId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long questionId){
        try{
            questionService.deleteQuestion(questionId);
            return ResponseEntity.status(HttpStatus.OK).body("Question removed successfully");
        }catch (RuntimeException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getAll());
    }

    @GetMapping("/getQuestionById/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(questionService.getQuestionById(questionId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }
}
