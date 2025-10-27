package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;


@Entity
@Table(name = "submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "score")
    private Double score;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private Users student;

    @ManyToOne
    @JoinColumn(name = "quiz_id",referencedColumnName = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    @ElementCollection
    @CollectionTable(name = "submission_answers",
    joinColumns = @JoinColumn(name = "submission_id")
    )
    @MapKeyColumn(name = "answer")
    @Column(name = "answers")
    private Map<Long,String> answers;
}
