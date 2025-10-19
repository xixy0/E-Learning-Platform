package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentSubmissionId;;

    private String assignmentSubmissionUrl;

    private LocalDateTime submissionDate;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Users users;

    @ManyToOne
    @JoinColumn(name = "assignment_id",referencedColumnName = "assignment_id")
    @JsonIgnore
    private Assignment assignment;
}
