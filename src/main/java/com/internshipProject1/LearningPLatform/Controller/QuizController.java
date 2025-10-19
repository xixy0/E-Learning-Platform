package com.internshipProject1.LearningPLatform.Controller;

import com.internshipProject1.LearningPLatform.DTO.QuizDTO;
import com.internshipProject1.LearningPLatform.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/quiz")
public class QuizController  {

    @Autowired
    private QuizService quizService;


    @PostMapping("/addQuiz/{courseId}")
    public ResponseEntity<?> addQuiz(@PathVariable Long courseId ,@RequestBody QuizDTO quizDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(quizService.addQuiz(courseId,quizDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/updateQuiz/{quizId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long quizId, @RequestBody QuizDTO quizDTO){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(quizService.updateQuiz(quizId,quizDTO));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteCourse/{quizId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long quizId){
        try{
            quizService.deleteQuiz(quizId);
            return ResponseEntity.status(HttpStatus.OK).body("Quiz removed successfully");
        }catch (RuntimeException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getQuizById/{quizId}")
    public ResponseEntity<?> getQuizById(@PathVariable Long quizId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(quizService.getQuizById(quizId));
        } catch (RuntimeException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(quizService.getAll());
    }


    @GetMapping("/getQuestions/{quizId}")
    public ResponseEntity<List<?>> getAllQuestions(@PathVariable Long quizId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(quizService.getQuestions(quizId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }

    @GetMapping("/getSubmissions/{quizId}")
    public ResponseEntity<List<?>> getSubmissions(@PathVariable Long quizId){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(quizService.getSubmissions(quizId));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(ex.getMessage()));
        }
    }
}
