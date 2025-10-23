package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentDTO {

    private Long enrollmentId;
    private LocalDate enrollmentDate;
    private String studentName;
    private Long userId;
    private Long courseId;

}
