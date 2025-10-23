package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {


    private Long assignmentId;
    private String assignmentTitle;
    private String assignmentDescription;
    private Long courseId;
    private List<String> assignmentPdfUrl;
}
