package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile file;

    public AssignmentDTO(Long assignmentId, String assignmentTitle, String assignmentDescription,Long courseId,List<String> assignmentPdfUrl) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.assignmentDescription = assignmentDescription;
        this.courseId = courseId;
        this.assignmentPdfUrl = assignmentPdfUrl;
    }
}
