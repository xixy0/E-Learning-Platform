package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="assignment_id")
    private Long assignmentId;

    @Column(name = "assignment_title")
    private String assignmentTitle;

    @Column(name = "assignment_desc")
    private String assignmentDescription;

    @Column(name = "assignmentPdf_url")
    private List<String> assignmentPdfUrl;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "course_id")
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    List<AssignmentSubmission> assignmentSubmissions;

}
